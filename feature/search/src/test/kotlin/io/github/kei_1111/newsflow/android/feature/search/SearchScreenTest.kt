package io.github.kei_1111.newsflow.android.feature.search

import androidx.compose.ui.test.junit4.createComposeRule
import io.github.kei_1111.newsflow.android.core.test.NewsflowTestRunner
import io.github.kei_1111.newsflow.android.feature.search.fixture.SearchTestFixtures
import io.github.kei_1111.newsflow.android.feature.search.robot.SearchScreenRobot
import io.github.kei_1111.newsflow.library.feature.search.SearchIntent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode

@RunWith(NewsflowTestRunner::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var robot: SearchScreenRobot

    @Before
    fun setup() {
        robot = SearchScreenRobot(composeTestRule)
    }

    // === Error State Tests ===

    @Test
    fun errorState_displaysErrorContent() {
        robot
            .setupWithState(SearchTestFixtures.createErrorState())
            .verifySearchScreenDisplayed()
            .verifyErrorContentDisplayed()
            .verifyRetryButtonDisplayed()
    }

    @Test
    fun errorState_networkError_displaysErrorContent() {
        robot
            .setupWithState(SearchTestFixtures.createNetworkErrorState())
            .verifyErrorContentDisplayed()
    }

    @Test
    fun errorState_serverError_displaysErrorContent() {
        robot
            .setupWithState(SearchTestFixtures.createServerErrorState())
            .verifyErrorContentDisplayed()
    }

    @Test
    fun errorState_retryButtonClick_emitsRetrySearchIntent() {
        val receivedIntents = mutableListOf<SearchIntent>()

        robot
            .setupWithState(
                state = SearchTestFixtures.createNetworkErrorState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickRetryButton()

        assertEquals(listOf(SearchIntent.RetrySearch), receivedIntents)
    }

    @Test
    fun errorState_internalError_retryButtonClick_emitsNavigateBackIntent() {
        val receivedIntents = mutableListOf<SearchIntent>()

        robot
            .setupWithState(
                state = SearchTestFixtures.createInternalErrorState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickRetryButton()

        assertEquals(listOf(SearchIntent.NavigateBack), receivedIntents)
    }

    // === Loading State Tests ===

    @Test
    fun searchingState_displaysLoadingIndicator() {
        robot
            .setupWithState(SearchTestFixtures.createSearchingState())
            .verifySearchScreenDisplayed()
            .verifyContentDisplayed()
            .verifyLoadingDisplayed()
    }

    @Test
    fun searchingState_displaysTopAppBar() {
        robot
            .setupWithState(SearchTestFixtures.createSearchingState())
            .verifyTopAppBarDisplayed()
            .verifySearchTextFieldDisplayed()
    }

    // === Stable State Tests ===

    @Test
    fun stableState_displaysContent() {
        robot
            .setupWithState(SearchTestFixtures.createStableState())
            .verifySearchScreenDisplayed()
            .verifyContentDisplayed()
            .verifyTopAppBarDisplayed()
            .verifySearchTextFieldDisplayed()
    }

    @Test
    fun stableState_withResults_displaysArticleList() {
        robot
            .setupWithState(SearchTestFixtures.createWithResultsState())
            .verifyArticleListDisplayed()
    }

    @Test
    fun stableState_emptyResults_displaysEmptyArticleList() {
        robot
            .setupWithState(SearchTestFixtures.createEmptyResultState())
            .verifyArticleListDisplayed()
    }

    // === TopAppBar Interaction Tests ===

    @Test
    fun backButtonClick_emitsNavigateBackIntent() {
        val receivedIntents = mutableListOf<SearchIntent>()

        robot
            .setupWithState(
                state = SearchTestFixtures.createStableState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickBackButton()

        assertEquals(listOf(SearchIntent.NavigateBack), receivedIntents)
    }

    @Test
    fun optionButtonClick_emitsShowOptionsSheetIntent() {
        val receivedIntents = mutableListOf<SearchIntent>()

        robot
            .setupWithState(
                state = SearchTestFixtures.createStableState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickOptionButton()

        assertEquals(listOf(SearchIntent.ShowOptionsSheet), receivedIntents)
    }

    // === SearchTextField Tests ===

    @Test
    fun clearButtonClick_emitsClearQueryIntent() {
        val receivedIntents = mutableListOf<SearchIntent>()

        robot
            .setupWithState(
                state = SearchTestFixtures.createWithResultsState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickClearButton()

        assertEquals(listOf(SearchIntent.ClearQuery), receivedIntents)
    }

    // === BottomSheet Tests ===

    @Test
    fun bottomSheet_displaysWhenArticleSelected() {
        robot
            .setupWithState(SearchTestFixtures.createWithSelectedArticleState())
            .verifyBottomSheetDisplayed()
    }

    @Test
    fun bottomSheet_notDisplayedWhenNoArticleSelected() {
        robot
            .setupWithState(SearchTestFixtures.createWithResultsState())
            .verifyBottomSheetNotDisplayed()
    }

    @Test
    fun moreButtonClick_emitsShowArticleOverviewIntent() {
        val receivedIntents = mutableListOf<SearchIntent>()

        robot
            .setupWithState(
                state = SearchTestFixtures.createWithResultsState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickFirstArticleMoreButton()

        assertEquals(1, receivedIntents.size)
        assertTrue(receivedIntents.first() is SearchIntent.ShowArticleOverview)
    }

    // === OptionBottomSheet Tests ===

    @Test
    fun optionBottomSheet_displaysWhenVisible() {
        robot
            .setupWithState(SearchTestFixtures.createWithOptionsSheetVisibleState())
            .verifyOptionBottomSheetDisplayed()
    }

    @Test
    fun optionBottomSheet_notDisplayedWhenNotVisible() {
        robot
            .setupWithState(SearchTestFixtures.createWithResultsState())
            .verifyOptionBottomSheetNotDisplayed()
    }

    // === Multiple Intent Tests ===

    @Test
    fun multipleBackButtonClicks_emitsMultipleIntents() {
        val receivedIntents = mutableListOf<SearchIntent>()

        robot
            .setupWithState(
                state = SearchTestFixtures.createStableState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickBackButton()
            .clickBackButton()

        assertEquals(
            listOf(SearchIntent.NavigateBack, SearchIntent.NavigateBack),
            receivedIntents
        )
    }
}
