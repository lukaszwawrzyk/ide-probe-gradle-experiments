plugins {
    scala
    application
}

repositories {
    mavenCentral()
    maven(url = "https://packages.jetbrains.team/maven/p/ij/intellij-dependencies") // for robot extension
}

val ideProbeVersion = "0.14.0"

dependencies {
    // Use Scala 2.13 in our library project
    implementation("org.virtuslab.ideprobe:driver_2.12:${ideProbeVersion}")
    implementation("org.virtuslab.ideprobe:robot-driver_2.12:${ideProbeVersion}")
}

application {
    mainClass.set("org.virtuslab.ideprobe.gradle.experiments.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}
