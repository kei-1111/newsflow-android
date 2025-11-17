plugins {
    alias(libs.plugins.newsflow.android.android.library.compose)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.core.ui"
}

dependencies {
    implementation(libs.androidx.material3)
}