plugins {
    alias(libs.plugins.newsflow.android.android.application)
}

android {
    namespace = "io.github.kei_1111.newsflow.android"

    defaultConfig {
        versionCode = 1
        versionName = "1.0"
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
}

dependencies {
    implementation(projects.feature.home)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3.expressive)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.android)
    implementation(libs.newsflow.library.shared)
}