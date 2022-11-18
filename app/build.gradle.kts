import Dependencies.addCoreModuleDependencies
import Dependencies.addCoreUiDependencies
import Dependencies.addTestDependencies

plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

apply(plugin = "com.google.gms.google-services")

android {

    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildTools

    defaultConfig {
        applicationId = "com.education.nycschools"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        multiDexEnabled = true
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

//    signingConfigs {
//        register("release") {
//            storeFile = file("releasekeystore.jks")
//            storePassword = "<storePassword>"
//            keyAlias = "<keyAlias>"
//            keyPassword = "<keyPassword>"
//        }
//    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
//            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    testOptions {
        unitTests {
            // Any other configurations
            all {
                it.maxHeapSize = "1024m"
            }
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

kapt { correctErrorTypes = true }

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.multiDex)
    implementation(Dependencies.splashScreen)
    addCoreModuleDependencies()
    addCoreUiDependencies()
    addTestDependencies()
}
