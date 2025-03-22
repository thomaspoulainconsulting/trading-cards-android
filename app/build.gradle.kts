import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.tpc.tradingcards"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tpc.tradingcards"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("room.incremental", "true")
            arg("room.expandProjection", "true")
        }
    }

    buildTypes {
        all {
            val properties = Properties()
            FileInputStream("local.properties").use { fileInputStream ->
                properties.load(fileInputStream)
            }

            buildConfigField("String", "API_POKEMON", properties.getProperty("pokemon-api-key"))
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material3.icons)
    implementation(libs.coil3)
    implementation(libs.coil3.network)
    implementation(libs.timber)
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.navigation.compose)
    implementation(libs.koin)
    implementation(libs.koin.compose)
    implementation(libs.compose.ui.util)
    implementation(libs.ui.animation)

    implementation(libs.bundles.network)

    testImplementation(libs.junit)
    
    androidTestImplementation(libs.bundles.tests)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}