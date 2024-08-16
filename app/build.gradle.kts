plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android.buildFeatures.buildConfig = true

android {
    namespace = "ru.cityron"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.cityron"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_17}"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    /** Hilt **/
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    implementation(libs.androidx.hilt.navigation)
    ksp(libs.hilt.compiler)
    /** Core **/
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation)

    implementation(libs.datastore)
    /** Serialization **/
    implementation(libs.kotlinx.serialization)
    implementation(libs.gson)
    /** Network **/
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    /** Libs **/
    implementation(libs.fingerprint.android)
    implementation(libs.highcharts.android)
    /** Room **/
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    /** WorkManager **/
    implementation(libs.work.runtime)
    /** RuStore **/
    implementation(platform(libs.rustore.bom))
    implementation(libs.rustore.pushclient)
}