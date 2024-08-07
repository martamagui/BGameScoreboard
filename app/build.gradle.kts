plugins {
    id(Plugins.application)
    id(Plugins.kotlinAndroid)
    id(Plugins.ksp)
    id(Plugins.hilt)
}

android {
    namespace = "com.mmag.bgamescoreboard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mmag.bgamescoreboard"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.all {
        val variant = this
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.ApkVariantOutputImpl
            val newApkName =
                "BGameScoreBoard-${variant.versionName}-${variant.name}.apk"
            output.outputFileName = newApkName
        }
    }
}

dependencies {

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.lifecycleRuntimeKtx)
    implementation(Libs.AndroidX.Compose.activityCompose)
    implementation(platform(Libs.AndroidX.Compose.composeBom))
    implementation(Libs.AndroidX.Compose.composeUi)
    implementation(Libs.AndroidX.Compose.composeUiGraphics)
    implementation(Libs.AndroidX.Compose.composeUiToolingPreview)
    implementation(Libs.AndroidX.Compose.composeMaterial3)
    implementation(Libs.AndroidX.Compose.navigationCompose)

    implementation(Libs.Hilt.hiltAndroid)
    ksp(Libs.Hilt.hiltAndroidCompiler)

    implementation(Libs.AndroidX.Compose.hiltNavigationCompose)

    implementation(Libs.Room.roomRuntime)
    annotationProcessor(Libs.Room.roomCompiler)
    ksp(Libs.Room.roomCompiler)
    implementation(Libs.Room.roomKtx)

    //For rememberLauncherForActivityResult()
    implementation("androidx.activity:activity-compose:1.8.2")

    //For PickVisualMedia contract
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    testImplementation(Libs.AndroidX.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.testExtJunit)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
    androidTestImplementation(Libs.AndroidX.Test.composeUiTestJUnit4)
    androidTestImplementation(platform(Libs.AndroidX.Compose.composeBom))
    debugImplementation(Libs.AndroidX.Compose.composeUiTooling)
    debugImplementation(Libs.AndroidX.Test.composeUiTestManifest)
}