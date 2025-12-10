package io.github.kei_1111.newsflow.android.feature.home

import androidx.compose.ui.test.junit4.createComposeRule
import io.github.kei_1111.newsflow.android.core.test.NewsflowTestRunner
import io.github.kei_1111.newsflow.android.feature.home.fixture.HomeTestFixtures
import io.github.kei_1111.newsflow.android.feature.home.robot.HomeScreenRobot
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import io.github.kei_1111.newsflow.library.feature.home.HomeIntent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode

@RunWith(NewsflowTestRunner::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var robot: HomeScreenRobot

    @Before
    fun setup() {
        robot = HomeScreenRobot(composeTestRule)
    }

    // === Error State Tests ===

    @Test
    fun errorState_displaysErrorContent() {
        robot
            .setupWithState(HomeTestFixtures.createErrorState())
            .verifyHomeScreenDisplayed()
            .verifyErrorContentDisplayed()
            .verifyRetryButtonDisplayed()
    }

    @Test
    fun errorState_networkError_displaysErrorContent() {
        robot
            .setupWithState(HomeTestFixtures.createNetworkErrorState())
            .verifyErrorContentDisplayed()
    }

    @Test
    fun errorState_serverError_displaysErrorContent() {
        robot
            .setupWithState(HomeTestFixtures.createServerErrorState())
            .verifyErrorContentDisplayed()
    }

    @Test
    fun errorState_retryButtonClick_emitsRetryLoadIntent() {
        val receivedIntents = mutableListOf<HomeIntent>()

        robot
            .setupWithState(
                state = HomeTestFixtures.createErrorState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickRetryButton()

        assertEquals(listOf(HomeIntent.RetryLoad), receivedIntents)
    }

    // === Loading State Tests ===

    @Test
    fun loadingState_displaysLoadingIndicator() {
        robot
            .setupWithState(HomeTestFixtures.createLoadingState())
            .verifyHomeScreenDisplayed()
            .verifyContentDisplayed()
            .verifyLoadingDisplayed()
    }

    @Test
    fun loadingState_displaysTopAppBarAndTabRow() {
        robot
            .setupWithState(HomeTestFixtures.createLoadingState())
            .verifyTopAppBarDisplayed()
            .verifyTabRowDisplayed()
    }

    // === Stable State Tests ===

    @Test
    fun stableState_displaysContent() {
        robot
            .setupWithState(HomeTestFixtures.createStableState())
            .verifyHomeScreenDisplayed()
            .verifyContentDisplayed()
            .verifyTopAppBarDisplayed()
            .verifyTabRowDisplayed()
    }

    @Test
    fun stableState_withArticles_displaysArticleList() {
        robot
            .setupWithState(HomeTestFixtures.createStableState())
            .verifyArticleListDisplayed()
    }

    @Test
    fun stableState_emptyArticles_displaysEmptyArticleList() {
        robot
            .setupWithState(HomeTestFixtures.createEmptyState())
            .verifyArticleListDisplayed()
    }

    // === TopAppBar Interaction Tests ===

    @Test
    fun searchButtonClick_emitsNavigateSearchIntent() {
        val receivedIntents = mutableListOf<HomeIntent>()

        robot
            .setupWithState(
                state = HomeTestFixtures.createStableState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickSearchButton()

        assertEquals(listOf(HomeIntent.NavigateSearch), receivedIntents)
    }

    // === Category State Tests ===

    @Test
    fun stableState_generalCategory_displaysCorrectly() {
        robot.setupWithState(
            HomeTestFixtures.createStableState(currentCategory = NewsCategory.GENERAL)
        )
            .verifyContentDisplayed()
            .verifyTabRowDisplayed()
    }

    @Test
    fun stableState_businessCategory_displaysCorrectly() {
        robot.setupWithState(
            HomeTestFixtures.createStableState(currentCategory = NewsCategory.BUSINESS)
        )
            .verifyContentDisplayed()
            .verifyTabRowDisplayed()
    }

    @Test
    fun stableState_technologyCategory_displaysCorrectly() {
        robot.setupWithState(
            HomeTestFixtures.createStableState(currentCategory = NewsCategory.TECHNOLOGY)
        )
            .verifyContentDisplayed()
            .verifyTabRowDisplayed()
    }

    // === Refreshing State Tests ===

    @Test
    fun refreshingState_displaysContent() {
        robot
            .setupWithState(HomeTestFixtures.createRefreshingState())
            .verifyContentDisplayed()
            .verifyArticleListDisplayed()
    }

    // === Multiple Intent Tests ===

    @Test
    fun multipleRetryClicks_emitsMultipleIntents() {
        val receivedIntents = mutableListOf<HomeIntent>()

        robot
            .setupWithState(
                state = HomeTestFixtures.createErrorState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickRetryButton()
            .clickRetryButton()

        assertEquals(
            listOf(HomeIntent.RetryLoad, HomeIntent.RetryLoad),
            receivedIntents
        )
    }

    // === BottomSheet Tests ===

    @Test
    fun bottomSheet_displaysWhenArticleSelected() {
        robot
            .setupWithState(HomeTestFixtures.createStateWithSelectedArticle())
            .verifyBottomSheetDisplayed()
    }

    @Test
    fun bottomSheet_notDisplayedWhenNoArticleSelected() {
        robot
            .setupWithState(HomeTestFixtures.createStableState())
            .verifyBottomSheetNotDisplayed()
    }

    @Test
    fun moreButtonClick_emitsShowArticleOverviewIntent() {
        val receivedIntents = mutableListOf<HomeIntent>()

        robot
            .setupWithState(
                state = HomeTestFixtures.createStableState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickFirstArticleMoreButton()

        assertEquals(1, receivedIntents.size)
        assertTrue(receivedIntents.first() is HomeIntent.ShowArticleOverview)
    }

    // === Tab Interaction Tests ===

    @Test
    fun tabClick_emitsChangeCategoryIntent() {
        val receivedIntents = mutableListOf<HomeIntent>()

        robot
            .setupWithState(
                state = HomeTestFixtures.createAllCategoriesState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickTab(NewsCategory.BUSINESS)

        assertTrue(
            receivedIntents.any { it == HomeIntent.ChangeCategory(NewsCategory.BUSINESS) }
        )
    }

    @Test
    fun tabClick_differentCategory_emitsCorrectIntent() {
        val receivedIntents = mutableListOf<HomeIntent>()

        robot
            .setupWithState(
                state = HomeTestFixtures.createAllCategoriesState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickTab(NewsCategory.TECHNOLOGY)

        assertTrue(
            receivedIntents.any { it == HomeIntent.ChangeCategory(NewsCategory.TECHNOLOGY) }
        )
    }
}
