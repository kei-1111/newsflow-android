import com.android.build.api.dsl.ApplicationExtension
import io.github.kei_1111.newsflow.android.configureAndroidCompose
import io.github.kei_1111.newsflow.android.configureAndroidKotlin
import io.github.kei_1111.newsflow.android.implementation
import io.github.kei_1111.newsflow.android.libs
import io.github.kei_1111.newsflow.android.localProperties
import io.github.kei_1111.newsflow.android.versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("newsflow.android.detekt")
            }

            dependencies {
                implementation(project(":core:designsystem"))
                implementation(project(":core:ui"))
            }

            extensions.configure<ApplicationExtension> {
                configureAndroidKotlin(this)
                configureAndroidCompose(this)

                defaultConfig {
                    targetSdk = libs.versions("targetSdk").toInt()

                    buildConfigField(
                        type = "String",
                        name = "NEWS_API_KEY",
                        value = "\"${localProperties.getProperty("NEWS_API_KEY") ?: ""}\"",
                    )
                }

                buildFeatures.buildConfig = true

                packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}