plugins {
    alias(libs.plugins.newsflow.android.android.feature)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.feature.home"
}

dependencies {
    implementation(libs.newsflow.library.home)
}
