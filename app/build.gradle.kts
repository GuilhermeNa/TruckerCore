plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.crashlytics)
    alias(libs.plugins.safe.args)
    alias(libs.plugins.parcelize)
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
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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

    flavorDimensions += listOf("product")
    productFlavors {
        create("individual") {
            dimension = "product"
            applicationIdSuffix = ".individual"
            versionNameSuffix = "-individual"
            manifestPlaceholders["appName"] = "Trucker"
        }

        create("business_admin") {
            dimension = "product"
            applicationIdSuffix = ".business_admin"
            versionNameSuffix = "-business_admin"
            manifestPlaceholders["appName"] = "Trucker Empresa"
        }

        create("business_driver") {
            dimension = "product"
            applicationIdSuffix = ".business_driver"
            versionNameSuffix = "-business_driver"
            manifestPlaceholders["appName"] = "Trucker Motorista"
        }
    }

}

dependencies {

    androidTestImplementation(libs.androidx.espresso.core)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.kotlinx.co.test)
    testImplementation(libs.mockk)
    debugImplementation(libs.leak.canary)

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

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.data.store.preferences)
    implementation(libs.coil)
    implementation(libs.coil.gif)
    implementation(libs.country.picker)
    implementation(libs.otp.view)

}

tasks.withType<Test> {
    useJUnitPlatform()
}
