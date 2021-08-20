package org.virtuslab.ideprobe.gradle.experiments

import java.nio.file.Path
import org.virtuslab.ideprobe.Extensions.PathExtension
import org.virtuslab.ideprobe.benchmark.report.ConsoleBenchmarkReporter
import org.virtuslab.ideprobe.{Assertions, IdeProbeFixture, IntelliJFixture}
import org.virtuslab.ideprobe.benchmark.{Benchmark, BenchmarkResult, BenchmarkRunner, BenchmarkSuite}
import org.virtuslab.ideprobe.robot.RobotPluginExtension

class ProjectGenerator(moduleCount: Int, moduleSize: Int = 20) {
  private val rootPackage = "example"

  def generate(root: Path): Unit = {
    val modules = (1 to moduleCount).map { moduleNum =>
      val moduleName = s"module$moduleNum"
      val moduleRoot = root.createDirectory(moduleName)
      val sourceRoot = moduleRoot.createDirectory(s"src/main/java/$rootPackage")
      moduleRoot.resolve("build.gradle").write(
        """
          |plugins {
          |    id 'java-library'
          |}
          |""".stripMargin)
      for (javaFileNum <- 1 to moduleSize) {
        val className = s"GeneratedClass$javaFileNum"
        sourceRoot.resolve(s"$className.java").write(
          s"""
            |package $rootPackage
            |
            |class $className {
            |  public void example() {
            |    System.out.println("Hello");
            |  }
            |}
            |""".stripMargin)
      }
      moduleName
    }
    val includeModules = modules.grouped(200)
      .map(modules => modules.map(module => s"'$module'").mkString("\ninclude(", ", ", ")"))
      .mkString
    root.resolve("settings.gradle").edit(content => content + includeModules)
  }
}

class GradleBenchmark(
  name: String,
  projectGenerator: ProjectGenerator,
  adjustFixture: IntelliJFixture => IntelliJFixture = identity
)
  extends Benchmark(name, 1, 2)
  with IdeProbeFixture with RobotPluginExtension with Assertions {

  override protected def run(runner: BenchmarkRunner): BenchmarkResult = {
    runner.run { measure =>
      val fixture = adjustFixture(fixtureFromConfig())
      fixture.run { intelliJ =>
        projectGenerator.generate(intelliJ.workspace)
        measure {
          intelliJ.probe.withRobot.openProject(intelliJ.workspace)
        }
        intelliJ.probe.projectModel()
      }
    }
  }
}

object Main extends App {
  val benchmarks = List(10, 100, 200, 500, 1000, 2000).map(size => new GradleBenchmark(size.toString, new ProjectGenerator(size)))
  BenchmarkSuite("open-project", benchmarks).run(new ConsoleBenchmarkReporter)
}
