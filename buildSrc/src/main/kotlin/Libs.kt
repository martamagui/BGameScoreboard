object Libs {
    object AndroidX {
        val coreKtx by lazy { "androidx.core:core-ktx:${Version.coreKtx}" }
        val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleRuntimeKtx}" }
        val appCompact by lazy { "androidx.appcompat:appcompat:${Version.appCompact}" }

        object Test {
            val junit by lazy { "junit:junit:${Version.Junit}" }
            val testExtJunit by lazy { "androidx.test.ext:junit:${Version.TestExtJunit}" }
            val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Version.EspressoCore}" }
            val composeUiTestManifest by lazy { "androidx.compose.ui:ui-test-manifest" }
            val composeUiTestJUnit4 by lazy { "androidx.compose.ui:ui-test-junit4" }
        }

        object Compose {
            val activityCompose by lazy { "androidx.activity:activity-compose:${Version.activityCompose}" }
            val composeBom by lazy { "androidx.compose:compose-bom:${Version.composeBom}" }
            val composeUi by lazy { "androidx.compose.ui:ui" }
            val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
            val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
            val composeMaterial3 by lazy { "androidx.compose.material3:material3" }
            val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling" }
            val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Version.hiltNavigationCompose}" }
            val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Version.navigaionCompose}" }
        }
    }

    object Room {
        val roomRuntime by lazy { "androidx.room:room-runtime:${Version.room}" }
        val roomCompiler by lazy { "androidx.room:room-compiler:${Version.room}" }
        val roomKtx by lazy { "androidx.room:room-ktx:${Version.room}" }
    }

    object Hilt{
        val hiltAndroid by lazy {  "com.google.dagger:hilt-android:${Version.hilt}"}
        val hiltAndroidCompiler by lazy {  "com.google.dagger:hilt-android-compiler:${Version.hilt}"}
    }

}
