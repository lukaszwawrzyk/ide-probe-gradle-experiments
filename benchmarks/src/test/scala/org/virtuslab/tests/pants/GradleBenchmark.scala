package org.virtuslab.tests.pants

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.virtuslab.ideprobe.benchmark.report.ConsoleBenchmarkReporter
import org.virtuslab.ideprobe.{Assertions, IdeProbeFixture, IntelliJFixture}
import org.virtuslab.ideprobe.benchmark.{Benchmark, BenchmarkResult, BenchmarkRunner, BenchmarkSuite}
import org.virtuslab.ideprobe.dependencies.IntelliJVersion
import org.virtuslab.ideprobe.robot.RobotPluginExtension

class GradleBenchmark(name: String, adjustFixture: IntelliJFixture => IntelliJFixture)
  extends Benchmark(name, 1, 3)
  with IdeProbeFixture with RobotPluginExtension with Assertions {

  override protected def run(runner: BenchmarkRunner): BenchmarkResult = {
    runner.run { measure =>
      val fixture = adjustFixture(fixtureFromConfig())
      fixture.run { intelliJ =>
        measure {
          intelliJ.probe.withRobot.openProject(intelliJ.workspace)
        }
        intelliJ.probe.projectModel()
      }
    }
  }
}

@RunWith(classOf[JUnit4])
class Main {
  @Test def main(): Unit = {
    val bench2021_1 = new GradleBenchmark(name = "2021.1", { fixture =>
      fixture.withVersion(IntelliJVersion.release("2021.1.1", build = "211.7142.45"))
    })
    val bench2020_3 = new GradleBenchmark(name = "2020.3", { fixture =>
      fixture.withVersion(IntelliJVersion.release("2021.3.4", build = "203.8084.24"))
    })

    BenchmarkSuite("default", Seq(bench2021_1, bench2020_3)).run(new ConsoleBenchmarkReporter)
  }
}
