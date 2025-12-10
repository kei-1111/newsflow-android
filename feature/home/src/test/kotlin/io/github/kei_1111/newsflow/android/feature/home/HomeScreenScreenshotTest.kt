package io.github.kei_1111.newsflow.android.feature.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.kei_1111.newsflow.android.core.test.NewsflowTestRunner
import io.github.kei_1111.newsflow.android.core.test.setNewsflowContent
import io.github.kei_1111.newsflow.android.feature.home.fixture.HomeTestFixtures
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(NewsflowTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel6a)
class HomeScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // === Error State Screenshots ===

    @Test
    fun homeScreen_errorState_networkFailure() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createNetworkErrorState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_errorState_serverError() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createServerErrorState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Loading State Screenshots ===

    @Test
    fun homeScreen_loadingState() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createLoadingState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_loadingState_businessCategory() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createLoadingState(
                    currentCategory = NewsCategory.BUSINESS
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Stable State Screenshots ===

    @Test
    fun homeScreen_stableState_withArticles() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_stableState_emptyArticles() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createEmptyState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Category Variations Screenshots ===

    @Test
    fun homeScreen_stableState_businessCategory() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(
                    currentCategory = NewsCategory.BUSINESS
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_stableState_technologyCategory() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(
                    currentCategory = NewsCategory.TECHNOLOGY
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_stableState_entertainmentCategory() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(
                    currentCategory = NewsCategory.ENTERTAINMENT
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_stableState_sportsCategory() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(
                    currentCategory = NewsCategory.SPORTS
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_stableState_scienceCategory() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(
                    currentCategory = NewsCategory.SCIENCE
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_stableState_healthCategory() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(
                    currentCategory = NewsCategory.HEALTH
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Refreshing State Screenshots ===

    @Test
    fun homeScreen_refreshingState() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createRefreshingState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Article Count Variations ===

    @Test
    fun homeScreen_stableState_singleArticle() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(
                    articlesByCategory = mapOf(
                        NewsCategory.GENERAL to HomeTestFixtures.createArticles(count = 1)
                    )
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_stableState_manyArticles() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeTestFixtures.createStableState(
                    articlesByCategory = mapOf(
                        NewsCategory.GENERAL to HomeTestFixtures.createArticles(count = 10)
                    )
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
