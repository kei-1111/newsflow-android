# newsflow-android Kotlin/Compose Style Guide

# Introduction
This style guide outlines the coding conventions for Kotlin and Jetpack Compose code in the newsflow-android project.
It follows Material 3 Expressive guidelines and project-specific conventions to ensure consistency across the codebase.

**All review comments must be written in Japanese.**

# Key Principles
* **Material 3 Expressive:** Follow Material 3 Expressive guidelines for all UI components.
* **Readability:** Code should be easy to understand for all team members.
* **Consistency:** Adhering to a consistent style across all modules improves collaboration.
* **Animation:** Use smooth, expressive animations for state transitions.

# Architecture

## Module Structure
* **UI Layer Only:** This repository handles only the UI layer.
* **External KMP Library:** ViewModels and business logic are provided by `newsflow.library.*`.
* **Dependency Flow:** `app` → `feature/*` → `core/*` → External KMP Library

## Convention Plugins
* **Feature Module:** Use `newsflow.android.android.feature` plugin.
* **Compose Library:** Use `newsflow.android.android.library.compose` plugin.
* **Basic Library:** Use `newsflow.android.android.library` plugin.

# Jetpack Compose

## State Transitions
* **Use `AnimatedContent`:** For state-based content switching instead of plain `when` expressions.
* **Use `MotionScheme.expressive()`:** Enable expressive motion for smooth animations.
    ```kotlin
    AnimatedContent(
        targetState = uiState,
        label = "ScreenContent",
    ) { targetUiState ->
        when (targetUiState) {
            is UiState.Loading -> LoadingContent()
            is UiState.Success -> SuccessContent(targetUiState)
            is UiState.Error -> ErrorContent(targetUiState.error)
        }
    }
    ```

## Preview Annotations
* **Screen Previews:** Use `@ScreenPreviews` for full screen composables (Light/Dark, Phone/Tablet, Standard/Large font).
* **Component Previews:** Use `@ComponentPreviews` for individual components (Light/Dark, Standard/Large font).
* **PreviewParameter:** Use `@PreviewParameter` with `CollectionPreviewParameterProvider` for multiple preview states.
    ```kotlin
    @Composable
    @ScreenPreviews
    private fun HomeScreenPreview(
        @PreviewParameter(HomeScreenPPP::class) parameter: HomeScreenPreviewParameter,
    ) {
        NewsflowAndroidTheme {
            HomeScreen(
                uiState = parameter.uiState,
                onUiAction = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
    ```

## Material 3 Expressive Components
* **Loading:** Use `ContainedLoadingIndicator` instead of `CircularProgressIndicator` for short waits.
* **Bottom Toolbar:** Use `DockedToolbar` instead of `BottomAppBar`.
* **Floating Toolbar:** Use `FloatingToolbar` for horizontal/vertical toolbars.

# Naming Conventions

## Components
* **Common Components:** Prefix with `Newsflow`: `NewsflowButton`, `NewsflowTab`
* **Feature Components:** No prefix required: `ArticleCard`, `ErrorContent`

## Resources
* **Icons:** Use `ic_` prefix with snake_case: `ic_arrow_back`, `ic_settings`
* **Images:** Use `img_` prefix with snake_case: `img_banner`, `img_article_preview`

## Callbacks
* **Pattern:** `on + Action + Target`
* **Click Actions:** `onClickRetryButton`, `onClickArticleCard`
* **Dismiss Actions:** `onDismissDialog`, `onDismissBottomSheet`
* **Change Actions:** `onChangeSearchQuery`, `onChangeCategory`

## Version Catalog (libs.versions.toml)
* **Versions:** Use camelCase: `androidGradlePlugin`, `kotlinVersion`
* **Libraries:** Use kebab-case: `androidx-core-ktx`, `koin-android`
* **Plugins:** Use kebab-case: `android-application`, `kotlin-serialization`

# Code Quality

## Static Analysis
* **Detekt:** Run `./gradlew detekt` before creating PRs.
* **Zero Issues:** All Detekt issues must be resolved.

## Best Practices
* **No Unused Code:** Remove unused imports, variables, and functions.
* **No Magic Numbers:** Define constants for numeric values.
* **Early Return:** Prefer early returns to reduce nesting.

# Tooling
* **Static Analyzer:** Detekt - Enforces code quality and style violations.
* **Build System:** Gradle with Convention Plugins for consistent module configuration.

# Example
```kotlin
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onUiAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        AnimatedContent(
            targetState = uiState,
            label = "HomeScreenContent",
        ) { targetUiState ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                when (targetUiState) {
                    is HomeUiState.Stable -> {
                        HomeContent(
                            uiState = targetUiState,
                            onUiAction = onUiAction,
                        )
                    }

                    is HomeUiState.Error -> {
                        ErrorContent(
                            error = targetUiState.error,
                            onClickActionButton = { onUiAction(HomeUiAction.OnClickRetryButton) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@ScreenPreviews
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPPP::class) parameter: HomeScreenPreviewParameter,
) {
    NewsflowAndroidTheme {
        HomeScreen(
            uiState = parameter.uiState,
            onUiAction = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

private data class HomeScreenPreviewParameter(
    val uiState: HomeUiState,
)

private class HomeScreenPPP : CollectionPreviewParameterProvider<HomeScreenPreviewParameter>(
    collection = listOf(
        HomeScreenPreviewParameter(
            uiState = HomeUiState.Stable(
                isLoading = false,
                currentNewsCategory = NewsCategory.GENERAL,
                articlesByCategory = emptyMap()
            ),
        ),
        HomeScreenPreviewParameter(
            uiState = HomeUiState.Error(
                error = NewsflowError.NetworkError.NetworkFailure("Network Failure")
            )
        )
    )
)
```