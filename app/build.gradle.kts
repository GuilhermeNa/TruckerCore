plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.crashlytics)
}

android {
    namespace = "com.example.truckercore"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.truckercore"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }

 /*   flavorDimensions += listOf("product", "version")
    productFlavors {
        create("limited") {
            dimension = "version"
            applicationIdSuffix = ".limited"
            versionNameSuffix = "-limited"
        }
        create("full") {
            dimension = "version"
            applicationIdSuffix = ".full"
            versionNameSuffix = "-full"
        }

        create("individual") {
            dimension = "product"
            applicationIdSuffix = ".individual"
            versionNameSuffix = "-individual"
        }
        create("business_admin") {
            dimension = "product"
            applicationIdSuffix = ".integrated_admin"
            versionNameSuffix = "-integrated_admin"
        }
        create("business_driver") {
            dimension = "product"
            applicationIdSuffix = ".integrated_driver"
            versionNameSuffix = "-integrated_driver"
        }*/
    //}
}

dependencies {

    androidTestImplementation(libs.androidx.espresso.core)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.kotlinx.co.test)
    testImplementation(libs.mockk)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(kotlin("reflect"))
    implementation(libs.koin)
    implementation(libs.koin.test)
    implementation(platform(libs.google.bom))
    implementation(libs.google.firebase)
    implementation(libs.google.analytics)
    implementation(libs.google.crashlytics)
    implementation(libs.google.auth)
    implementation(libs.google.storage)

}

tasks.withType<Test> {
    useJUnitPlatform()
}
