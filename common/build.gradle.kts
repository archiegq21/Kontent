import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlinx-serialization")
}

android {
    compileSdk = Android.sdkVersion
    defaultConfig {
        minSdk = Android.minSdkVersion
        targetSdk = Android.targetSdkVersion
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType(KotlinCompile::class.java).all {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

dependencies {
    coreLibraryDesugaring(Libs.Desugar.desugar_libs)
}

kotlin {
    android()
    // TODO revert to `ios()` once
    //  https://github.com/cashapp/sqldelight/issues/2044#issuecomment-721299517.
    //  is resolved
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    version = "1.0"
    cocoapods {
        summary = "Common Library"
        homepage = "https://www.quibbly.com"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.Kotlin.Coroutines.core)
                api(Libs.Kotlin.Ktor.core)
                implementation(Libs.Kotlin.Ktor.json)
                api(Libs.Kotlin.Ktor.serialization)
                implementation(Libs.Kotlin.Ktor.logging)

                implementation(Libs.Kotlin.DateTime.core)

                implementation(Libs.Koin.core)

                implementation(Libs.SqlDelight.runtime)
                implementation(Libs.SqlDelight.coroutines_ktx)

                implementation(Libs.MultipaltformSettings.no_args)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                // Ktor
                implementation(Libs.Kotlin.Coroutines.android)
                implementation(Libs.Kotlin.Ktor.android_core)
                implementation(Libs.Kotlin.Ktor.logback)

                implementation(Libs.SqlDelight.android)
            }
        }
        val androidTest by getting {
            dependencies {

            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Libs.Kotlin.Ktor.ios_core)
                implementation(Libs.Kotlin.Ktor.native_logging)

                implementation(Libs.SqlDelight.ios)
            }
        }
        val iosTest by getting
    }
}

sqldelight {
    database("KontentDatabase") {
        packageName = "com.quibbly.common.db"
    }
}