import java.util.Properties

plugins {
    id("kotlin-kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.digginroom.digginroom"
    compileSdk = 33
    packagingOptions {
        exclude("META-INF/LICENSE*")
    }
    buildFeatures {
        buildConfig = true
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }

    defaultConfig {
        applicationId = "com.digginroom.digginroom"
        minSdk = 28
        targetSdk = 33
        versionCode = 6
        versionName = "1.3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument(
            "runnerBuilder",
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
        )

        val properties = Properties().apply {
            file("../config.properties").inputStream().use {
                load(it)
            }
        }
        buildConfigField("String", "SERVER_URL", properties["server.url"] as String)
        buildConfigField("String", "CLIENT_ID", properties["server.clientId"] as String)
        buildConfigField(
            "String",
            "KAKAO_NATIVE_KEY",
            properties["kakao.nativeKey"] as String
        )

        val kakaoNativeKey = properties["kakao.nativeKey"] as String
        manifestPlaceholders["kakaoNativeKey"] = kakaoNativeKey.replace("\"", "")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
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
        jvmTarget = "17"
    }
    dataBinding {
        isEnabled = true
    }
}

dependencies {
    // project
    implementation(project(":domain"))
    implementation(project(":di"))
    implementation(project(":android-di"))

    // RoomPager
    implementation("com.github.DYGames:roompager:1.0.3@aar")

    // android
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")

    // network
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.google.android.gms:play-services-auth:20.6.0")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-crashlytics")

    // custom view
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // kakao
    implementation("com.kakao.sdk:v2-all:2.16.0")

    // fragment
    implementation("androidx.fragment:fragment-ktx:1.4.0")

    // lottie
    implementation("com.airbnb.android:lottie:5.2.0")

    // test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
