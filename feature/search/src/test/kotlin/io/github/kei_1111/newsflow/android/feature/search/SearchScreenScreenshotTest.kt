package io.github.kei_1111.newsflow.android.feature.search

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.kei_1111.newsflow.android.core.test.NewsflowTestRunner
import io.github.kei_1111.newsflow.android.core.test.setNewsflowContent
import io.github.kei_1111.newsflow.android.feature.search.fixture.SearchTestFixtures
import io.github.kei_1111.newsflow.library.feature.search.model.DateRangePreset
import io.github.kei_1111.newsflow.library.feature.search.model.SearchLanguage
import io.github.kei_1111.newsflow.library.feature.search.model.SortBy
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode

@RunWith(NewsflowTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class SearchScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // === Error State Screenshots ===

    @Test
    fun searchScreen_errorState_networkFailure() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createNetworkErrorState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_errorState_serverError() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createServerErrorState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_errorState_internalError() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createInternalErrorState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Loading State Screenshots ===

    @Test
    fun searchScreen_searchingState() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createSearchingState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_searchingState_withQuery() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createSearchingState(query = "Android development"),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Stable State Screenshots ===

    @Test
    fun searchScreen_stableState_empty() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createStableState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_stableState_withResults() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithResultsState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_stableState_emptyResults() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createEmptyResultState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Article Count Variations ===

    @Test
    fun searchScreen_stableState_singleArticle() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithResultsState(
                    articles = SearchTestFixtures.createArticles(count = 1)
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_stableState_manyArticles() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithResultsState(
                    articles = SearchTestFixtures.createArticles(count = 10)
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Search Options Variations ===

    @Test
    fun searchScreen_stableState_sortByPopularity() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithCustomOptionsState(
                    sortBy = SortBy.POPULARITY
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_stableState_sortByNewest() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithCustomOptionsState(
                    sortBy = SortBy.PUBLISHED_AT
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_stableState_dateRangeLastWeek() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithCustomOptionsState(
                    dateRange = DateRangePreset.LAST_WEEK
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_stableState_languageJapanese() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithCustomOptionsState(
                    language = SearchLanguage.JAPANESE
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Query Variations ===

    @Test
    fun searchScreen_stableState_shortQuery() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithResultsState(query = "AI"),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun searchScreen_stableState_longQuery() {
        composeTestRule.setNewsflowContent {
            SearchScreen(
                state = SearchTestFixtures.createWithResultsState(
                    query = "Kotlin Multiplatform Mobile development best practices"
                ),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
