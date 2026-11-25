plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.example.simpleproxy"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.simpleproxy"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"
    }
}
