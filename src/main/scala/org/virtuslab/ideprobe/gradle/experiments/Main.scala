package org.virtuslab.ideprobe.gradle.experiments

import org.virtuslab.ideprobe.{Config, IdeProbeFixture}
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
        |      "-Xms12288m",
        |      "-Xmx12288m",
        |      "-XX:+UseCodeCacheFlushing",
        |      "-XX:ReservedCodeCacheSize=1024m",
        |      "-XX:+UseG1GC",
        |      "-XX:SoftRefLRUPolicyMSPerMB=50",
        |      "-XX:CICompilerCount=2",
        |      "-XX:+HeapDumpOnOutOfMemoryError",
        |      "-XX:-OmitStackTraceInFastThrow",
        |      "-Dsun.io.useCanonPrefixCache=false",
        |      "-Dsun.io.useCanonCaches=false",
        |      "-Djava.net.preferIPv4Stack=true",
        |      "-Djdk.attach.allowAttachSelf=true",
        |      "-Djdk.module.illegalAccess.silent=true",
        |      "-Dkotlinx.coroutines.debug=off",
        |      "-Dfile.encoding=UTF-8",
        |      "-Dgit.blocking.read=false",
        |      "-Dvcs.log.index.git=false",
        |      "-XX:ErrorFile=$USER_HOME/java_error_in_idea_%p.log",
        |      "-XX:HeapDumpPath=$USER_HOME/java_error_in_idea.hprof",
        |      "-Dide.no.platform.update=true"
        |    ]
        |  }
        |}
        |
        |""".stripMargin)
    fixtureFromConfig(config).run { intelliJ =>
      intelliJ.probe.withRobot.openProject(intelliJ.workspace)
      intelliJ.probe.projectModel()
    }
  }

}
