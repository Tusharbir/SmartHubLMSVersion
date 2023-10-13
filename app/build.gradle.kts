plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "cseb.tech.smarthublms"
    compileSdk = 33

    defaultConfig {
        applicationId = "cseb.tech.smarthublms"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore:24.7.0")
    implementation("com.google.firebase:firebase-auth:22.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-analytics")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")// Use the latest version
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Gson converter for JSON parsing
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation ("com.sun.mail:android-mail:1.6.5")
    implementation ("com.sun.mail:android-activation:1.6.5")

    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")

    implementation("androidx.cardview:cardview:1.0.0")


    implementation("androidx.multidex:multidex:2.0.1")

    implementation ("com.github.PhilJay:MPAndroidChart:3.1.0")










}