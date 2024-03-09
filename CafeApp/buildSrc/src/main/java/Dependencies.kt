object Dependencies {

    object UI {
        const val Material = "com.google.android.material:material:${Versions.Material}"
        const val SwipeRefresh =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.SwipeRefresh}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"
        const val ViewBinding =
            "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Versions.ViewBinding}"
        const val ViewBindingRefl =
            "com.github.kirich1409:viewbindingpropertydelegate:${Versions.ViewBinding}"
        const val Coil = "io.coil-kt:coil:${Versions.Coil}"
        const val Keyboard =
            "net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:${Versions.Keyboard}"
    }

    object Compose {
        private const val material = "1.2.0"
        private const val materialV1 = "1.6.2" //1.1.1
        private const val windowSizeUtils = "1.2.0"
        private const val materialThemeAdapter = "1.2.1" //1.1.16
        private const val tooling = "1.6.2" //"1.4.3"

        const val ComposeNavigation = "androidx.navigation:navigation-compose:2.5.3"
        const val ComposeAnimatedNavigation = "com.google.accompanist:accompanist-navigation-animation:0.34.0" //0.27.1
        const val MaterialV1 = "androidx.compose.material:material:${materialV1}"
        const val MaterialV3 = "androidx.compose.material3:material3:${material}"
        const val WindowSizeUtils = "androidx.compose.material3:material3-window-size-class:${windowSizeUtils}"
        const val MaterialThemeAdapter = "com.google.android.material:compose-theme-adapter:${materialThemeAdapter}"
        const val ComposeUiTooling = "androidx.compose.ui:ui-tooling:${tooling}"
        const val ComposeToolingPreview = "androidx.compose.ui:ui-tooling-preview:${tooling}"
    }

    object AndroidX {
        const val AndroidX = "androidx.core:core-ktx:${Versions.CoreKtx}"
        const val FragmentKtx = "androidx.fragment:fragment-ktx:${Versions.FragmentKtx}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
        const val Preferences = "androidx.preference:preference-ktx:${Versions.Preferences}"
        const val Crypto = "androidx.security:security-crypto:${Versions.Crypto}"
    }

    object Navigation {
        const val NavFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.Navigation}"
        const val NavCompose = "androidx.navigation:navigation-compose:${Versions.NavigationCompose}"
        const val NavUI = "androidx.navigation:navigation-ui-ktx:${Versions.Navigation}"
    }

    object Lifecycle {
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Lifecycle}"
        const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Lifecycle}"
        const val Runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Lifecycle}"
        const val SavedState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.Lifecycle}"
        const val Service = "androidx.lifecycle:lifecycle-service:${Versions.Lifecycle}"
        const val Process = "androidx.lifecycle:lifecycle-process:${Versions.Lifecycle}"
    }

    object Kotlin {
        const val Serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Serialization}"
        const val Stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin}"
        const val Annotation = "com.android.support:support-annotations:${Versions.Annotation}"
    }

    object DI {
        const val HiltAndroid = "com.google.dagger:hilt-android:${Versions.Hilt}"
        const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt}"
        const val HiltNavFr = "androidx.hilt:hilt-navigation-fragment:${Versions.HiltNavFr}"
        const val HiltCommon = "androidx.hilt:hilt-common:${Versions.HiltWork}"
        const val HiltWork = "androidx.hilt:hilt-work:${Versions.HiltWork}"
        const val HiltNavCompose = "androidx.hilt:hilt-navigation-compose:${Versions.HiltNavCompose}"
    }

    object Room {
        const val Runtime = "androidx.room:room-runtime:${Versions.Room}"
        const val Compiler = "androidx.room:room-compiler:${Versions.Room}"
        const val Ktx = "androidx.room:room-ktx:${Versions.Room}"
    }

    object Network {
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"
        const val ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.Retrofit}"
        const val Okttp = "com.squareup.okhttp3:okhttp:${Versions.Okhttp}"
        const val Interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.Okhttp}"
        const val ConverterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.Retrofit}"
        const val GoogleGson = "com.google.code.gson:gson:${Versions.GoogleGson}"
        const val NetworkAdapter =
            "com.github.haroldadmin:NetworkResponseAdapter:${Versions.NetworkAdapter}"
    }

    object Stripe {
        const val Android = "com.stripe:stripe-android:${Versions.Stripe}"
        const val SDK = "com.stripe:stripeterminal:${Versions.StripeSDK}"
    }

    object Launchdarkly {
        const val Client = "com.launchdarkly:launchdarkly-android-client-sdk:${Versions.Launchdarkly}"
    }

    object ML {
        const val Barcode = "com.google.mlkit:barcode-scanning:${Versions.Scanning}"
    }

    object Camera {
        const val Camera2 = "androidx.camera:camera-camera2:${Versions.Camera}"
        const val CLifecycle = ("androidx.camera:camera-lifecycle:${Versions.Camera}")
        const val View = "androidx.camera:camera-view:${Versions.CameraView}"
    }

    object CodeQuality {
        const val DetektFormatting =
            "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.Detekt}"
    }

    object Pusher {
        const val PusherClient = "com.pusher:pusher-java-client:${Versions.Pusher}"
    }

    object CircleIndicator {
        const val CircleIndicator = "me.relex:circleindicator:${Versions.CircleIndicator}"
    }

    object Guava {
        const val Guava = "com.google.guava:guava:${Versions.Guava}"
    }

    object Libs {
        val JarLibs = mapOf("dir" to "libs", "include" to listOf("*.jar"))
    }

    object Sentry {
        const val Client = "io.sentry:sentry-android:${Versions.Sentry}"
        const val Navigation = "io.sentry:sentry-android-navigation:${Versions.SentryNavigation}"
        const val Fragment = "io.sentry:sentry-android-fragment:${Versions.SentryFragment}"
    }

    object Test {
        const val Junit = "junit:junit:${Versions.Junit}"
        const val Jupiter = "org.junit.jupiter:junit-jupiter:${Versions.Jupiter}"
        const val KTest = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.Kotlin}"
        const val Core = "androidx.arch.core:core-testing:${Versions.CoreTesting}"
        const val Coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Coroutine}"
        const val Truth = "com.google.truth:truth:${Versions.Truth}"
        const val Mockito = "org.mockito:mockito-core:${Versions.Mockito}"
        const val KMockito = "org.mockito.kotlin:mockito-kotlin:5.2.1" //4.0.0
        const val Room = "androidx.room:room-testing:${Versions.Room}"
        const val Assertk = "com.willowtreeapps.assertk:assertk-jvm:${Versions.Assert}"

        const val JUnitExt = "androidx.test.ext:junit:${Versions.JunitExt}"
        const val Espresso = "androidx.test.espresso:espresso-core:${Versions.Espresso}"
        const val MockitoAndroid = "org.mockito:mockito-android:${Versions.MockitoAndroid}"
        const val Navigation = "androidx.navigation:navigation-testing:${Versions.Navigation}"
    }

    object Sunmi {
        const val Printer = "com.sunmi:printerlibrary:${Versions.Sunmi}"
    }

    object Starmicronics {
        const val Stario = "com.starmicronics:stario:${Versions.Stario}"
        const val StarioSettings = "com.starmicronics:starioextension:${Versions.StarExtension}"
        const val StarioExtension = "com.starmicronics:stariodevicesetting:${Versions.StarSettings}"
        const val Stario10 = "com.starmicronics:stario10:${Versions.Stario10}"
    }

    object Intercom {
        const val Base = "io.intercom.android:intercom-sdk-base:${Versions.IntercomBase}"
        const val SDK = "io.intercom.android:intercom-sdk:${Versions.IntercomSDK}"
    }

    object Firebase {
        const val Messaging = "com.google.firebase:firebase-messaging:${Versions.FirebaseMessaging}"
    }

    object Work {
        const val Runtime = "androidx.work:work-runtime-ktx:${Versions.Work}"
    }

    object Desugar {
        const val Core = "com.android.tools:desugar_jdk_libs:${Versions.Desugar}"
    }
}