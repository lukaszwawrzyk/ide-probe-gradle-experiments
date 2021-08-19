import sbt._

object Dependencies {
  object ideProbe {
    val version = "0.15.0"
    val resolvers = Seq(
      MavenRepository(
        "jetbrains-3rd",
        "https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    )

    def apply(name: String): ModuleID = {
      "org.virtuslab.ideprobe" %% name % version
    }

    val benchmarks = apply("benchmarks")
    val driver = apply("driver")
    val robotDriver = apply("robot-driver")
  }

}
