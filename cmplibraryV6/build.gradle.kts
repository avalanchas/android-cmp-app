plugins {
    id("com.android.library")
    id("maven-publish")
    id("com.jfrog.bintray")
    kotlin("android")
    kotlin("android.extensions")
}

val versionLib = project.property("VERSION_NAME_V6") as String

group = "com.sourcepoint.cmplibrary"
version = versionLib

android {
    compileSdkVersion(28)
    testOptions.unitTests.isIncludeAndroidResources = true
    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(28)
        versionCode = 300
        versionName = versionLib
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "LOGGER_URL", "\"https://wrapper-api.sp-prod.net/metrics/v1/custom-metrics\"")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "LOGGER_URL", "\"https://wrapper-api.sp-prod.net/metrics/v1/custom-metrics\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to "*.jar", "dir" to "libs")))

    // kotlin
    implementation(Libs.kotlinxCoroutinesCore)
    implementation(Libs.kotlinReflect)

    // Unfortunately we depend on a outdated version of okhttp due to its support to older versions of Android
    //noinspection GradleDependency
    api(project(":cmplibrary"))

    testImplementation (Libs.mockk)
    testImplementation("androidx.test:core:1.2.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.4.0")
    testImplementation("junit:junit:4.13")
    testImplementation("org.json:json:20180813")
    testImplementation("org.mockito:mockito-core:2.19.0")
    testImplementation("org.robolectric:robolectric:4.2.1")
    androidTestImplementation("junit:junit:4.13")
}

apply(from = "${rootDir}/gradleutils/publish-android.gradle")