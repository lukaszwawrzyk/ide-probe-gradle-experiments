import sbt._

object Dependencies {
  val junit = Seq(
    "junit" % "junit" % "4.12" % Test,
    ("com.novocode" % "junit-interface" % "0.11" % Test).exclude("junit", "junit-dep")
  )

  object ideProbe {
    val version = "0.14.0"
    val resolvers = Seq(
      MavenRepository(
        "jetbrains-3rd",
        "https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    )

    def apply(name: String): ModuleID = {
      "org.virtuslab.ideprobe" %% name % version
    }

    val benchmarks = apply("benchmarks")
    val jUnitDriver = apply("junit-driver")
    val robotDriver = apply("robot-driver")
  }

}