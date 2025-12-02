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
* **Use plain `when` expression:** For state-based content switching.
* **Use `MotionScheme.expressive()`:** Enable expressive motion for smooth animations.
    ```kotlin
    when (state) {
        is State.Loading -> LoadingContent()
        is State.Success -> SuccessContent(state)
        is State.Error -> ErrorContent(state.error)
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
                state = parameter.state,
                onIntent = {},
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
* **Philosophy:** Components focus on View only, no knowledge of Intent/Action. Callbacks describe "what happened".
* **Pattern:** `on + 操作/イベント + UI要素`
* **UI component types omitted:** `onClickArticle` ✅, `onClickArticleCard` ❌
* **Click:** `onClickArticle`, `onClickMore`
* **Dismiss:** `onDismissDialog`, `onDismissBottomSheet`
* **Change:** `onChangeQuery`, `onChangeCategory`
* **XXXButton components:** Use `onClick` only: `ShareButton(onClick = ...)`

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
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is HomeState.Stable -> {
                    HomeContent(
                        state = state,
                        onIntent = onIntent,
                    )
                }

                is HomeState.Error -> {
                    ErrorContent(
                        error = state.error,
                        onClickActionButton = { onIntent(HomeIntent.RetryLoad) }
                    )
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
            state = parameter.state,
            onIntent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

private data class HomeScreenPreviewParameter(
    val state: HomeState,
)

private class HomeScreenPPP : CollectionPreviewParameterProvider<HomeScreenPreviewParameter>(
    collection = listOf(
        HomeScreenPreviewParameter(
            state = HomeState.Stable(
                isLoading = false,
                currentNewsCategory = NewsCategory.GENERAL,
                articlesByCategory = emptyMap()
            ),
        ),
        HomeScreenPreviewParameter(
            state = HomeState.Error(
                error = NewsflowError.NetworkError.NetworkFailure("Network Failure")
            )
        )
    )
)
```