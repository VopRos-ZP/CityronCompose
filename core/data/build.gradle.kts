plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.cityron.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_17}"
    }
}

dependencies {
    implementation(project(":core:domain"))
    /** Hilt **/
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    /** Ktor **/
    implementation(libs.bundles.ktor)
    /** Room **/
    api(libs.room.ktx)
    api(libs.room.runtime)
    ksp(libs.room.compiler)
    /** Datastore **/
    implementation(libs.datastore)
    /** Fingerprint **/
    implementation(libs.fingerprint.android)
}