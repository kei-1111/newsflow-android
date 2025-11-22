plugins {
    alias(libs.plugins.newsflow.android.android.library.compose)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.core.designsystem"
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.androidx.material3.expressive)
    implementation(libs.newsflow.library.model)
}