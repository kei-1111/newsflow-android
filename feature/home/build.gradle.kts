plugins {
    alias(libs.plugins.newsflow.android.android.feature)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.feature.home"

    defaultConfig {
        buildConfigField("String", "DRAWABLE_PATH", "\"${projectDir}/src/main/res/drawable\"")
    }

    buildFeatures.buildConfig = true
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.newsflow.library.home)
}
