package codeanalyzetools

import Dependencies
import Versions
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

apply<DetektPlugin>()

plugins {
    id("io.gitlab.arturbosch.detekt")
}

configure<DetektExtension> {
    autoCorrect = true
    toolVersion = Versions.Detekt
    parallel = false
    source = files(
        "src/main/kotlin",
        "src/main/java"
    )
    config = files("${project.rootDir}/buildSrc/detekt.yml")
}

dependencies {
    detektPlugins(Dependencies.CodeQuality.DetektFormatting)
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        html.outputLocation.set(file("${rootProject.layout.buildDirectory}/build/reports/detekt/detekt-report.html")) //project.buildDir
        xml.required.set(true)
        xml.outputLocation.set(file("${rootProject.layout.buildDirectory}/build/reports/detekt/detekt-report.xml")) //project.buildDir
    }
}

tasks.withType<Detekt>().configureEach {
    include("**/*.kt", "**/*.kts")
    exclude("**/build/**", ".*/resources/.*", ".*test.*,.*/resources/.*,.*/tmp/.*")

    jvmTarget = JavaVersion.VERSION_11.toString()
}