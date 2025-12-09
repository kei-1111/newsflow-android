package io.github.kei_1111.newsflow.android.feature.viewer

import androidx.compose.ui.test.junit4.createComposeRule
import io.github.kei_1111.newsflow.android.core.test.NewsflowTestRunner
import io.github.kei_1111.newsflow.android.feature.viewer.fixture.ViewerTestFixtures
import io.github.kei_1111.newsflow.android.feature.viewer.robot.ViewerScreenRobot
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerIntent
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode

@RunWith(NewsflowTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ViewerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var robot: ViewerScreenRobot

    @Before
    fun setup() {
        robot = ViewerScreenRobot(composeTestRule)
    }

    // === Init State Tests ===

    @Test
    fun initState_displaysLoading() {
        robot
            .setupWithState(ViewerTestFixtures.createInitState())
            .verifyViewerScreenDisplayed()
            .verifyLoadingDisplayed()
    }

    // === Loading State Tests ===

    @Test
    fun loadingState_displaysLoading() {
        robot
            .setupWithState(ViewerTestFixtures.createLoadingState())
            .verifyViewerScreenDisplayed()
            .verifyLoadingDisplayed()
    }

    // === Stable State Tests ===

    @Test
    fun stableState_displaysContent() {
        robot
            .setupWithState(ViewerTestFixtures.createStableState())
            .verifyViewerScreenDisplayed()
            .verifyContentDisplayed()
    }

    @Test
    fun stableState_displaysTopAppBar() {
        robot
            .setupWithState(ViewerTestFixtures.createStableState())
            .verifyTopAppBarDisplayed()
    }

    // === Error State Tests ===

    @Test
    fun errorState_displaysErrorContent() {
        robot
            .setupWithState(ViewerTestFixtures.createErrorState())
            .verifyViewerScreenDisplayed()
            .verifyErrorContentDisplayed()
            .verifyRetryButtonDisplayed()
    }

    @Test
    fun errorState_networkError_displaysErrorContent() {
        robot
            .setupWithState(ViewerTestFixtures.createNetworkErrorState())
            .verifyErrorContentDisplayed()
    }

    @Test
    fun errorState_serverError_displaysErrorContent() {
        robot
            .setupWithState(ViewerTestFixtures.createServerErrorState())
            .verifyErrorContentDisplayed()
    }

    // === TopAppBar Interaction Tests ===

    @Test
    fun backButtonClick_emitsNavigateBackIntent() {
        val receivedIntents = mutableListOf<ViewerIntent>()

        robot
            .setupWithState(
                state = ViewerTestFixtures.createStableState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickBackButton()

        assertEquals(listOf(ViewerIntent.NavigateBack), receivedIntents)
    }

    @Test
    fun shareButtonClick_emitsShareArticleIntent() {
        val receivedIntents = mutableListOf<ViewerIntent>()

        robot
            .setupWithState(
                state = ViewerTestFixtures.createStableState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickShareButton()

        assertEquals(listOf(ViewerIntent.ShareArticle), receivedIntents)
    }

    // === Error State Interaction Tests ===

    @Test
    fun errorState_retryButtonClick_emitsNavigateBackIntent() {
        val receivedIntents = mutableListOf<ViewerIntent>()

        robot
            .setupWithState(
                state = ViewerTestFixtures.createErrorState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickRetryButton()

        assertEquals(listOf(ViewerIntent.NavigateBack), receivedIntents)
    }

    // === Multiple Interaction Tests ===

    @Test
    fun multipleBackButtonClicks_emitsMultipleIntents() {
        val receivedIntents = mutableListOf<ViewerIntent>()

        robot
            .setupWithState(
                state = ViewerTestFixtures.createStableState(),
                onIntent = { receivedIntents.add(it) }
            )
            .clickBackButton()
            .clickBackButton()

        assertEquals(
            listOf(ViewerIntent.NavigateBack, ViewerIntent.NavigateBack),
            receivedIntents
        )
    }
}
