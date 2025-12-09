import io.github.kei_1111.newsflow.android.implementation
import io.github.kei_1111.newsflow.android.library
import io.github.kei_1111.newsflow.android.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("newsflow.android.android.library.compose")
                apply("newsflow.android.android.ui.test")
            }

            dependencies {
                implementation(project(":core:designsystem"))
                implementation(project(":core:navigation"))
                implementation(project(":core:ui"))

                implementation(libs.library("androidx.material3.expressive"))
                implementation(libs.library("androidx.navigation3.runtime"))
                implementation(libs.library("koin.compose.viewmodel"))
            }
        }
    }
}