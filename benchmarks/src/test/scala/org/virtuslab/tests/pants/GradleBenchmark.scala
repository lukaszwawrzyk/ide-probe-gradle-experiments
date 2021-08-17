package org.virtuslab.tests.pants

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.virtuslab.ideprobe.benchmark.report.ConsoleBenchmarkReporter
import org.virtuslab.ideprobe.{Assertions, IdeProbeFixture}
import org.virtuslab.ideprobe.benchmark.{Benchmark, BenchmarkResult, BenchmarkRunner, BenchmarkSuite}
import org.virtuslab.ideprobe.robot.RobotPluginExtension

class GradleBenchmark
  extends Benchmark("open-project", 1, 3)
  with IdeProbeFixture with RobotPluginExtension with Assertions {

  override protected def run(runner: BenchmarkRunner): BenchmarkResult = {
    runner.run { measure =>
      fixtureFromConfig().run { intelliJ =>
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
    BenchmarkSuite("default", Seq(new GradleBenchmark)).run(new ConsoleBenchmarkReporter)
  }
}
