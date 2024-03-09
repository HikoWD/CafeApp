plugins {
    id(GradlePlugins.VersionCheck)
    id(GradlePlugins.CodeAnalyze)
    id(GradlePlugins.TasksTest)
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.BIN
    gradleVersion = Versions.Gradle
}

buildscript {
    val kotlin_version by extra("1.9.22")
    dependencies {
        classpath("com.google.gms:google-services:4.4.1") //4.3.3
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }

    repositories {
        mavenCentral()
    }
}