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
                files = arrayOf<Any>(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                ),
            )
        }
    }
}

dependencies {
    implementation(projects.core.navigation)
    implementation(projects.feature.home)
    implementation(projects.feature.viewer)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.expressive)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.koin.android)
    implementation(libs.newsflow.library.shared)
}