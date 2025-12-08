plugins {
    alias(libs.plugins.newsflow.android.android.feature)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.feature.home"

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
    implementation(libs.newsflow.library.home)

    testImplementation(project(":core:test"))
}
