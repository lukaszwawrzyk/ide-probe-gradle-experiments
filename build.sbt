scalaVersion := "2.13.3"
name := "gradle-experiments"

val ideProbeVersion = "0.13.1"

libraryDependencies += "org.virtuslab.ideprobe" %% "driver" % ideProbeVersion
libraryDependencies += "org.virtuslab.ideprobe" %% "robot-driver" % ideProbeVersion

ThisBuild / resolvers += MavenRepository(
  "jetbrains-3rd",
  "https://packages.jetbrains.team/maven/p/ij/intellij-dependencies"
)
