package io.github.kei_1111.newsflow.android.feature.home.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.performClick
import io.github.kei_1111.newsflow.android.core.designsystem.DesignSystemTestTags
import io.github.kei_1111.newsflow.android.core.test.onTag
import io.github.kei_1111.newsflow.android.core.test.setNewsflowContent
import io.github.kei_1111.newsflow.android.feature.home.HomeScreen
import io.github.kei_1111.newsflow.android.feature.home.HomeTestTags
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import io.github.kei_1111.newsflow.library.feature.home.HomeIntent
import io.github.kei_1111.newsflow.library.feature.home.HomeState
import org.robolectric.shadows.ShadowSystemClock
import java.time.Duration

class HomeScreenRobot(
    private val composeTestRule: ComposeContentTestRule
) {
    // === Setup Methods ===

    fun setupWithState(
        state: HomeState,
        onIntent: (HomeIntent) -> Unit = {}
    ): HomeScreenRobot = apply {
        composeTestRule.setNewsflowContent {
            HomeScreen(state = state, onIntent = onIntent)
        }
    }

    // === Action Methods ===

    fun clickRetryButton(): HomeScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.ActionButton).performClick()
    }

    fun clickSearchButton(): HomeScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(HomeTestTags.TopAppBar.SearchButton).performClick()
    }

    fun clickFirstArticleMoreButton(): HomeScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule
            .onTag(DesignSystemTestTags.ArticleCard.moreButton("article_0"))
            .performClick()
    }

    fun clickArticleMoreButton(articleId: String): HomeScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule
            .onTag(DesignSystemTestTags.ArticleCard.moreButton(articleId))
            .performClick()
    }

    fun clickTab(category: NewsCategory): HomeScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(HomeTestTags.TabRow.tab(category)).performClick()
    }

    // === Verification Methods ===

    fun verifyHomeScreenDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.HomeScreen.Root).assertIsDisplayed()
    }

    fun verifyErrorContentDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.Root).assertIsDisplayed()
    }

    fun verifyRetryButtonDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.ActionButton).assertIsDisplayed()
    }

    fun verifyLoadingDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.Loading.Root).assertIsDisplayed()
    }

    fun verifyContentDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.Content.Root).assertIsDisplayed()
    }

    fun verifyTabRowDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.TabRow.Root).assertIsDisplayed()
    }

    fun verifyTopAppBarDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.TopAppBar.Root).assertIsDisplayed()
    }

    fun verifyArticleListDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.ArticleList.Root).assertIsDisplayed()
    }

    fun verifyBottomSheetDisplayed(): HomeScreenRobot = apply {
        composeTestRule.waitForIdle()
        composeTestRule
            .onTag(DesignSystemTestTags.ArticleOverviewBottomSheet.Root)
            .assertIsDisplayed()
    }

    fun verifyBottomSheetNotDisplayed(): HomeScreenRobot = apply {
        composeTestRule.waitForIdle()
        composeTestRule
            .onTag(DesignSystemTestTags.ArticleOverviewBottomSheet.Root)
            .assertIsNotDisplayed()
    }

    fun verifyTabSelected(category: NewsCategory): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.TabRow.tab(category)).assertIsDisplayed()
    }

    // === Private Helpers ===

    private fun advanceDebounceTime() {
        ShadowSystemClock.advanceBy(Duration.ofMillis(DEBOUNCE_TIME_MS))
    }

    companion object {
        private const val DEBOUNCE_TIME_MS = 600L
    }
}
