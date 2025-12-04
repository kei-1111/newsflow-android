import java.util.Properties

val localProperties = Properties().apply {
    val localPropertiesFile = file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        includeBuild("build-logic")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/kei-1111/newsflow-library")
            credentials {
                username = localProperties.getProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
                password = localProperties.getProperty("gpr.token") ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

rootProject.name = "newsflow-android"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:designsystem")
include(":core:navigation")
include(":core:ui")
include(":feature:home")
include(":feature:search")
include(":feature:viewer")
