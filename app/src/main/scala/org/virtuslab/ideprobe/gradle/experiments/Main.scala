package org.virtuslab.ideprobe.gradle.experiments

import org.virtuslab.ideprobe.Extensions.PathExtension
import org.virtuslab.ideprobe.{Config, IdeProbeFixture, Shell}
import org.virtuslab.ideprobe.robot.RobotPluginExtension

object Main extends IdeProbeFixture with RobotPluginExtension {

  def main(args: Array[String]): Unit = {
    val config = Config.fromString(
      """
        |probe {
        |  workspace.path = "classpath:/seed"
        |  intellij {
        |    version {
        |      build = "212.4746.92"
        |      release = "2021.2"
        |    }
        |  }
        |  driver {
        |    vmOptions = [
        |      "-ea",
        |      "-Xms2G",
        |      "-Dfile.encoding=UTF-8"
        |    ]
        |  }
        |}
        |
        |""".stripMargin)


    val fixture = fixtureFromConfig(config)
    fixture.run { intelliJ =>
      intelliJ.workspace.directChildren().foreach(println)
      println("A")
      intelliJ.probe.listOpenProjects()
      println("B")
      intelliJ.probe.openProject(intelliJ.workspace)
      println("C")
      intelliJ.probe.projectModel()
      println("D")
    }
    println("end")
    Shell.run("ps", "aux")
    sys.exit(0)
  }

}
