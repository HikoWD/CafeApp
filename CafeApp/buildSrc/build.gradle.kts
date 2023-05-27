plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("AppPlugin") {
            id = "AppPlugin"
            implementationClass = "plugins.AppPlugin"
        }
    }
}

object Deps {
    const val ANDROID_GRADLE = "com.android.tools.build:gradle:7.3.0"
    const val KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20"
    const val HILT_GRADLE = "com.google.dagger:hilt-android-gradle-plugin:2.38.1"
    const val NAVIGATION = "androidx.navigation:navigation-safe-args-gradle-plugin:2.4.1"
    const val VERSION_CHECKER = "com.github.ben-manes:gradle-versions-plugin:0.39.0"
    const val KTLINT = "org.jlleitschuh.gradle:ktlint-gradle:10.2.1"
    const val SPOTLESS = "com.diffplug.spotless:spotless-plugin-gradle:6.0.0"
    const val DETEKT = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0"
    const val DOKKA = "org.jetbrains.dokka:dokka-gradle-plugin:1.6.0"
    const val JACOCO = "org.jacoco:org.jacoco.core:0.8.8"
}

dependencies {
    implementation(gradleApi())
    implementation(Deps.ANDROID_GRADLE)
    implementation(Deps.KOTLIN_GRADLE)
    implementation(Deps.HILT_GRADLE)
    implementation(Deps.NAVIGATION)
    implementation(Deps.VERSION_CHECKER)
    implementation(Deps.KTLINT)
    implementation(Deps.SPOTLESS)
    implementation(Deps.DETEKT)
    implementation(Deps.DOKKA)
    implementation(Deps.JACOCO)
}