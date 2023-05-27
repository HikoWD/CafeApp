package codeanalyzetools

plugins {
    id("codeanalyzetools.ktlint")
    id("codeanalyzetools.spotless")
    id("codeanalyzetools.detekt")
}

tasks.getByName("check") {
    setDependsOn(
        listOf(
            tasks.getByName("ktlintFormat"),
            tasks.getByName("spotlessApply"),

            tasks.getByName("ktlintCheck"),
            tasks.getByName("spotlessCheck"),
            tasks.getByName("detekt")
        )
    )
}
