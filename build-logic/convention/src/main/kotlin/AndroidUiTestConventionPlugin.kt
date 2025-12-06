import com.android.build.gradle.LibraryExtension
import io.github.kei_1111.newsflow.android.debugImplementation
import io.github.kei_1111.newsflow.android.library
import io.github.kei_1111.newsflow.android.libs
import io.github.kei_1111.newsflow.android.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidUiTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.github.takahirom.roborazzi")
            }

            extensions.configure<LibraryExtension> {
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                        all {
                            it.systemProperty("robolectric.graphicsMode", "NATIVE")
                            it.maxHeapSize = "2g"
                        }
                    }
                }
            }

            dependencies {
                testImplementation(libs.library("junit"))
                testImplementation(libs.library("robolectric"))
                testImplementation(platform(libs.library("androidx-compose-bom")))
                testImplementation(libs.library("androidx-compose-ui-test-junit4"))
                debugImplementation(libs.library("androidx-compose-ui-test-manifest"))
                testImplementation(libs.library("roborazzi"))
                testImplementation(libs.library("roborazzi-compose"))
                testImplementation(libs.library("roborazzi-junit-rule"))
            }
        }
    }
}