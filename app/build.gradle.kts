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
    implementation("com.google.firebase:firebase-storage:19.2.")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-database")


    implementation("androidx.cardview:cardview:1.0.0")


    implementation("androidx.multidex:multidex:2.0.1")

    implementation ("com.github.PhilJay:MPAndroidChart:3.1.0")










}