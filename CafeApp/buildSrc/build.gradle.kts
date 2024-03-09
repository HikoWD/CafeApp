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
    const val ANDROID_GRADLE = "com.android.tools.build:gradle:8.2.2" //7.3.0
    const val KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22"
    const val HILT_GRADLE = "com.google.dagger:hilt-android-gradle-plugin:2.46" //2.46
    const val NAVIGATION = "androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7" //2.4.1
    const val VERSION_CHECKER = "com.github.ben-manes:gradle-versions-plugin:0.39.0"
    const val KTLINT = "org.jlleitschuh.gradle:ktlint-gradle:10.2.1"
    const val SPOTLESS = "com.diffplug.spotless:spotless-plugin-gradle:6.0.0"
    const val DETEKT = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.5"
    const val DOKKA = "org.jetbrains.dokka:dokka-gradle-plugin:1.6.0"
    const val JACOCO = "org.jacoco:org.jacoco.core:0.8.11"
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