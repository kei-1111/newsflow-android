plugins {
    alias(libs.plugins.newsflow.android.android.library.compose)
}

android {
    namespace = "io.github.kei_1111.newsflow.android.core.test"
}

dependencies {
    // テスト用依存関係をapiで公開
    api(libs.junit)
    api(libs.robolectric)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui.test.junit4)
    api(libs.roborazzi)
    api(libs.roborazzi.compose)
    api(libs.roborazzi.junit.rule)

    // テーマ用
    implementation(libs.androidx.material3.expressive)
    implementation(project(":core:ui"))
}
