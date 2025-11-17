import io.github.kei_1111.newsflow.android.configureDetekt
import io.github.kei_1111.newsflow.android.detektPlugins
import io.github.kei_1111.newsflow.android.library
import io.github.kei_1111.newsflow.android.libs
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.gitlab.arturbosch.detekt")
            }

            dependencies {
                detektPlugins(libs.library("detekt.compose"))
                detektPlugins(libs.library("detekt.formatting"))
            }

            configureDetekt(extensions.getByType<DetektExtension>())
        }
    }
}