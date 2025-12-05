plugins {
    alias(libs.plugins.newsflow.android.android.feature)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.feature.search"
}

dependencies {
    implementation(libs.newsflow.library.search)
}
