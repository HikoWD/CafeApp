object Configs {

    private const val versionMajor = 2

    private const val versionMinor = 1

    private const val versionPatch = 8

    const val Id = "by.ivan.CafeApp"

    val VersionCode: Int get() = 217

    val VersionName: String get() = "$versionMajor.$versionMinor.${versionPatch}"

    const val MinSdk = 23

    const val TargetSdk = 33

    const val CompileSdk = 33

    const val AndroidJunitRunner = "androidx.test.runner.AndroidJUnitRunner"

    val FreeCompilerArgs = listOf(
        "-Xopt-in=kotlin.RequiresOptIn",
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xopt-in=kotlinx.coroutines.InternalCoroutinesApi",
        "-Xopt-in=kotlinx.coroutines.FlowPreview",
        "-Xopt-in=kotlin.Experimental",
        "-Xjvm-default=all"
    )
}
