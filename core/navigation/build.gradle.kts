plugins {
    alias(libs.plugins.newsflow.android.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
}
