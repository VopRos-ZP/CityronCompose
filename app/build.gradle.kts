plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.vopros.cityron"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vopros.cityron"
        minSdk = 28
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
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    implementation(project(":m3:domain"))
    implementation(project(":m3:data"))
    /*** PRESENTATION ***/
    /** Core **/
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    /** Compose **/
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    /** DI **/
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    debugImplementation(libs.ui.tooling)
    ksp(libs.hilt.compiler)
    /** Navigation **/
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)
    /** HighCharts **/
    implementation(libs.highcharts)
}