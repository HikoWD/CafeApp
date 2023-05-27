
import extensions.*

plugins {
    id(GradlePlugins.AndroidApplication)
    id(GradlePlugins.KotlinAndroid)
    id(GradlePlugins.KotlinKapt)
    id(GradlePlugins.Hilt)
    id(GradlePlugins.Parcelize)
    id(GradlePlugins.NavSafeArgs)
//    id("io.sentry.android.gradle") version "3.2.0"
    id("org.jetbrains.kotlin.android")
//    id("com.google.gms.google-services")
    id(GradlePlugins.JACOCO)
    id(GradlePlugins.JACOCO_REPORT)
}

android {
    compileSdk = Configs.CompileSdk
    configureDefaultConfig(project)
    setCompileOptions()
    configureKotlinCompile()
    setSigningConfigs(project)

    defaultConfig {
        setProperty("archivesBaseName", defaultConfig.versionName + "-" + defaultConfig.versionCode + "-" + "cafe-app") //test-app
    }

    buildFeatures.apply {
        viewBinding = true
        compose = true
        sourceSets {
            named("main") {
                java.srcDir("src/main/jniLibs/")
            }
        }

        compileOptions {
            sourceCompatibility(JavaVersion.VERSION_1_8)
            targetCompatibility(JavaVersion.VERSION_1_8)
            isCoreLibraryDesugaringEnabled = true
        }
        kotlinOptions {
            jvmTarget = "1.8"

            buildFeatures.apply {
                viewBinding = true
            }

            packagingOptions {
                jniLibs.excludes.add("**/libjsc.so")
            }
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    jacoco {
        buildToolsVersion("0.8.5")
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    dependencies {
        implementation("androidx.datastore:datastore-preferences:1.0.0")
        implementation("io.coil-kt:coil-compose:2.3.0") //asyncImage
        implementation(Dependencies.Compose.MaterialV1)
        implementation(Dependencies.Compose.ComposeAnimatedNavigation)
        implementation(Dependencies.Compose.MaterialV3)
        implementation(Dependencies.Compose.WindowSizeUtils)
        implementation(Dependencies.Compose.ComposeToolingPreview)
        implementation(Dependencies.Compose.MaterialThemeAdapter)
        debugImplementation(Dependencies.Compose.ComposeUiTooling)

        implementation(Dependencies.UI.Material)
        implementation(Dependencies.UI.SwipeRefresh)
        implementation(Dependencies.UI.ConstraintLayout)
        implementation(Dependencies.UI.ViewBinding)
        implementation(Dependencies.UI.ViewBindingRefl)
        implementation(Dependencies.UI.Coil)
        implementation(Dependencies.UI.Keyboard)

        implementation(Dependencies.AndroidX.AndroidX)
        implementation(Dependencies.AndroidX.FragmentKtx)
        implementation(Dependencies.AndroidX.Preferences)
        implementation(Dependencies.AndroidX.Crypto)

        implementation(Dependencies.Navigation.NavFragment)
        implementation(Dependencies.Navigation.NavCompose)
        implementation(Dependencies.Navigation.NavUI)

        implementation(Dependencies.Lifecycle.ViewModel)
        implementation(Dependencies.Lifecycle.LiveData)
        implementation(Dependencies.Lifecycle.Runtime)
        implementation(Dependencies.Lifecycle.SavedState)
        implementation(Dependencies.Lifecycle.Service)
        implementation(Dependencies.Lifecycle.Process)

        implementation(Dependencies.Kotlin.Serialization)
        implementation(Dependencies.Kotlin.Stdlib)
        implementation(Dependencies.Kotlin.Annotation)

        kapt(Dependencies.DI.HiltCompiler)
        implementation(Dependencies.DI.HiltAndroid)
        implementation(Dependencies.DI.HiltNavFr)
        implementation(Dependencies.DI.HiltNavCompose)

        kapt(Dependencies.Room.Compiler)
        implementation(Dependencies.Room.Runtime)
        implementation(Dependencies.Room.Ktx)

        implementation(Dependencies.Network.Retrofit)
        implementation(Dependencies.Network.ConverterGson)
        implementation(Dependencies.Network.Okttp)
        implementation(Dependencies.Network.Interceptor)
        implementation(Dependencies.Network.ConverterScalars)
        implementation(Dependencies.Network.GoogleGson)
        implementation(Dependencies.Network.NetworkAdapter)

        implementation(Dependencies.Camera.Camera2)
        implementation(Dependencies.Camera.CLifecycle)
        implementation(Dependencies.Camera.View)

        implementation(Dependencies.Stripe.Android)
        implementation(Dependencies.Stripe.SDK)

//        implementation(Dependencies.ML.Barcode)

        implementation(Dependencies.Guava.Guava)

//        implementation(Dependencies.Pusher.PusherClient)

//        implementation(Dependencies.Launchdarkly.Client)

        implementation(Dependencies.CircleIndicator.CircleIndicator)

        implementation(fileTree(Dependencies.Libs.JarLibs))
//        implementation(Dependencies.Sentry.Client)
//        implementation(Dependencies.Sentry.Navigation)
//        implementation(Dependencies.Sentry.Fragment)
//        implementation(Dependencies.Sunmi.Printer)
//        implementation(Dependencies.Starmicronics.Stario)
//        implementation(Dependencies.Starmicronics.StarioExtension)
//        implementation(Dependencies.Starmicronics.StarioSettings)
//        implementation(Dependencies.Starmicronics.Stario10)
//        implementation(Dependencies.Intercom.Base)
//        implementation(Dependencies.Intercom.SDK)
//        implementation(Dependencies.Firebase.Messaging)
        implementation(Dependencies.Work.Runtime)
        implementation(Dependencies.DI.HiltCommon)
        implementation(Dependencies.DI.HiltWork)
        coreLibraryDesugaring(Dependencies.Desugar.Core)
        testImplementation(Dependencies.Test.Junit)
        testImplementation(Dependencies.Test.Jupiter)
        testImplementation(Dependencies.Test.KTest)
        testImplementation(Dependencies.Test.Core)
        testImplementation(Dependencies.Test.Coroutine)
        testImplementation(Dependencies.Test.Truth)
        testImplementation(Dependencies.Test.Mockito)
        testImplementation(Dependencies.Test.KMockito)
        testImplementation(Dependencies.Test.Room)
        testImplementation(Dependencies.Test.Assertk)
        androidTestImplementation(Dependencies.Test.Junit)
        androidTestImplementation(Dependencies.Test.Coroutine)
        androidTestImplementation(Dependencies.Test.Core)
        androidTestImplementation(Dependencies.Test.Truth)
        androidTestImplementation(Dependencies.Test.JUnitExt)
        androidTestImplementation(Dependencies.Test.Espresso)
        androidTestImplementation(Dependencies.Test.MockitoAndroid)
        androidTestImplementation(Dependencies.Test.Navigation)
    }
}
dependencies {
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.core:core-ktx:1.7.0")
}


//plugins {
//    id 'com.android.application'
//    id 'org.jetbrains.kotlin.android'
//}
//
//android {
//      namespace 'by.fro.testapp'
//    compileSdk 33
//
//    defaultConfig {
//        applicationId "by.fro.testapp"
//        minSdk 24
//        targetSdk 33
//        versionCode 1
//        versionName "1.0"
//
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary true
//        }
//    }
//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//    compileOptions {
//        sourceCompatibility JavaVersion.VERSION_1_8
//        targetCompatibility JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = '1.8'
//    }
//    buildFeatures {
//        compose true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion '1.2.0'
//    }
//    packagingOptions {
//        resources {
//            excludes += '/META-INF/{AL2.0,LGPL2.1}'
//        }
//    }
//}
//
//dependencies {
//
//    implementation 'androidx.core:core-ktx:1.7.0'
//    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'
//    implementation 'androidx.activity:activity-compose:1.3.1'
//    implementation "androidx.compose.ui:ui:$compose_ui_version"
//    implementation "androidx.compose.ui:ui-tooling-preview:$compose_ui_version"
//    implementation 'androidx.compose.material:material:1.2.0'
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
//    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_ui_version"
//    debugImplementation "androidx.compose.ui:ui-tooling:$compose_ui_version"
//    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_ui_version"
//}