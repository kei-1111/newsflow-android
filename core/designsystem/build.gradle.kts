plugins {
    alias(libs.plugins.newsflow.android.android.library.compose)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.core.designsystem"

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "DRAWABLE_PATH",
            value = "\"${projectDir}/src/main/res/drawable\""
        )
    }

    buildFeatures.buildConfig = true
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.androidx.material3.expressive)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.newsflow.library.model)
}