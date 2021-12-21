import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin(KotlinPlugins.multiplatform)
    kotlin(KotlinPlugins.cocoapods)
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id(Plugins.androidLibrary)
    id(Plugins.sqlDelight)
}

version = "1.1"

android {
    compileSdkVersion(Application.compileSdk)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(Application.minSdk)
        targetSdkVersion(Application.targetSdk)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {baseName = "sharedApp"}
        podfile = project.file("../IOSApp/Podfile")
    }

    sourceSets {
        val commonMain by getting {
            dependencies{
                implementation(Kotlinx.datetime)
                implementation(Ktor.core)
                implementation(Ktor.clientSerialization)
                implementation(SQLDelight.runtime)
            }
        }
        val androidMain by getting {
            dependencies{
                implementation(Ktor.android)
                implementation(Ktor.OkHttp)
                implementation(SQLDelight.androidDriver)
            }
        }
        val iosMain by getting{
            dependencies {
                implementation(Ktor.ios)
                implementation(SQLDelight.nativeDriver)
            }
        }
    }
}

sqldelight{
//    database("RecipeDataBase"){
//        packageName = "com.otero.recipetoshop.datasource.cacherecipe"
//        sourceFolders = listOf("sqldelight")
//    }
    database("FoodDataBase"){
        packageName = "com.otero.recipetoshop.datasource.cachedespensa"
        sourceFolders = listOf("sqldelight")
    }
}
