package io.github.kei_1111.newsflow.android.feature.search.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.performClick
import io.github.kei_1111.newsflow.android.core.designsystem.DesignSystemTestTags
import io.github.kei_1111.newsflow.android.core.test.onTag
import io.github.kei_1111.newsflow.android.core.test.setNewsflowContent
import io.github.kei_1111.newsflow.android.feature.search.SearchScreen
import io.github.kei_1111.newsflow.android.feature.search.SearchTestTags
import io.github.kei_1111.newsflow.library.feature.search.SearchIntent
import io.github.kei_1111.newsflow.library.feature.search.SearchState
import org.robolectric.shadows.ShadowSystemClock
import java.time.Duration

class SearchScreenRobot(
    private val composeTestRule: ComposeContentTestRule
) {
    // === Setup Methods ===

    fun setupWithState(
        state: SearchState,
        onIntent: (SearchIntent) -> Unit = {}
    ): SearchScreenRobot = apply {
        composeTestRule.setNewsflowContent {
            SearchScreen(state = state, onIntent = onIntent)
        }
    }

    // === Action Methods ===

    fun clickBackButton(): SearchScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(SearchTestTags.TopAppBar.BackButton).performClick()
    }

    fun clickOptionButton(): SearchScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(SearchTestTags.TopAppBar.OptionButton).performClick()
    }

    fun clickClearButton(): SearchScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(SearchTestTags.SearchTextField.ClearButton).performClick()
    }

    fun clickRetryButton(): SearchScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.ActionButton).performClick()
    }

    fun clickFirstArticleMoreButton(): SearchScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule
            .onTag(DesignSystemTestTags.ArticleCard.moreButton("article_0"))
            .performClick()
    }

    // === Verification Methods ===

    fun verifySearchScreenDisplayed(): SearchScreenRobot = apply {
        composeTestRule.onTag(SearchTestTags.SearchScreen.Root).assertIsDisplayed()
    }

    fun verifyContentDisplayed(): SearchScreenRobot = apply {
        composeTestRule.onTag(SearchTestTags.Content.Root).assertIsDisplayed()
    }

    fun verifyLoadingDisplayed(): SearchScreenRobot = apply {
        composeTestRule.onTag(SearchTestTags.Loading.Root).assertIsDisplayed()
    }

    fun verifyErrorContentDisplayed(): SearchScreenRobot = apply {
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.Root).assertIsDisplayed()
    }

    fun verifyArticleListDisplayed(): SearchScreenRobot = apply {
        composeTestRule.onTag(SearchTestTags.ArticleList.Root).assertIsDisplayed()
    }

    fun verifyTopAppBarDisplayed(): SearchScreenRobot = apply {
        composeTestRule.onTag(SearchTestTags.TopAppBar.Root).assertIsDisplayed()
    }

    fun verifySearchTextFieldDisplayed(): SearchScreenRobot = apply {
        composeTestRule.onTag(SearchTestTags.SearchTextField.Root).assertIsDisplayed()
    }

    fun verifyBottomSheetDisplayed(): SearchScreenRobot = apply {
        composeTestRule.waitForIdle()
        composeTestRule
            .onTag(DesignSystemTestTags.ArticleOverviewBottomSheet.Root)
            .assertIsDisplayed()
    }

    fun verifyBottomSheetNotDisplayed(): SearchScreenRobot = apply {
        composeTestRule.waitForIdle()
        composeTestRule
            .onTag(DesignSystemTestTags.ArticleOverviewBottomSheet.Root)
            .assertIsNotDisplayed()
    }

    fun verifyOptionBottomSheetDisplayed(): SearchScreenRobot = apply {
        composeTestRule.waitForIdle()
        composeTestRule.onTag(SearchTestTags.OptionBottomSheet.Root).assertIsDisplayed()
    }

    fun verifyOptionBottomSheetNotDisplayed(): SearchScreenRobot = apply {
        composeTestRule.waitForIdle()
        composeTestRule.onTag(SearchTestTags.OptionBottomSheet.Root).assertIsNotDisplayed()
    }

    fun verifyRetryButtonDisplayed(): SearchScreenRobot = apply {
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.ActionButton).assertIsDisplayed()
    }

    // === Private Helpers ===

    private fun advanceDebounceTime() {
        ShadowSystemClock.advanceBy(Duration.ofMillis(DEBOUNCE_TIME_MS))
    }

    companion object {
        private const val DEBOUNCE_TIME_MS = 600L
    }
}
