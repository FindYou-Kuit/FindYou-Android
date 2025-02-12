import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt)
    id("org.jetbrains.kotlin.kapt")
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.example.findu"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.findu"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", properties["base.url"].toString())
        buildConfigField("String", "GPT_KEY", properties["GPT_KEY"].toString())
        manifestPlaceholders["naverClientId"] = properties["NAVER_CLIENT_ID"].toString()
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    // Jetpack Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta01")
    
    // Indicator
    implementation("com.tbuonomo:dotsindicator:5.0")
    
    // Material Components
    implementation("com.google.android.material:material:1.9.0")
 

    // Naver Map
    implementation(libs.map.sdk)

    // Location Provider Client
    implementation(libs.play.services.location)

    // Web View
    implementation(libs.androidx.webkit)
    
    // Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    
    // Calender
    implementation("io.github.architshah248.calendar:awesome-calendar:2.0.0")
}