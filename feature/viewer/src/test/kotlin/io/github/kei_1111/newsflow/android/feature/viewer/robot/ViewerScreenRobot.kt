package io.github.kei_1111.newsflow.android.feature.viewer.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.performClick
import io.github.kei_1111.newsflow.android.core.designsystem.DesignSystemTestTags
import io.github.kei_1111.newsflow.android.core.test.onTag
import io.github.kei_1111.newsflow.android.core.test.setNewsflowContent
import io.github.kei_1111.newsflow.android.feature.viewer.ViewerScreen
import io.github.kei_1111.newsflow.android.feature.viewer.ViewerTestTags
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerIntent
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerState
import org.robolectric.shadows.ShadowSystemClock
import java.time.Duration

class ViewerScreenRobot(
    private val composeTestRule: ComposeContentTestRule
) {
    // === Setup Methods ===

    fun setupWithState(
        state: ViewerState,
        onIntent: (ViewerIntent) -> Unit = {}
    ): ViewerScreenRobot = apply {
        composeTestRule.setNewsflowContent {
            ViewerScreen(state = state, onIntent = onIntent)
        }
    }

    // === Action Methods ===

    fun clickBackButton(): ViewerScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(ViewerTestTags.TopAppBar.BackButton).performClick()
    }

    fun clickShareButton(): ViewerScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(ViewerTestTags.TopAppBar.ShareButton).performClick()
    }

    fun clickBookmarkButton(): ViewerScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(ViewerTestTags.TopAppBar.BookmarkButton).performClick()
    }

    fun clickRetryButton(): ViewerScreenRobot = apply {
        advanceDebounceTime()
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.ActionButton).performClick()
    }

    // === Verification Methods ===

    fun verifyViewerScreenDisplayed(): ViewerScreenRobot = apply {
        composeTestRule.onTag(ViewerTestTags.ViewerScreen.Root).assertIsDisplayed()
    }

    fun verifyLoadingDisplayed(): ViewerScreenRobot = apply {
        composeTestRule.onTag(ViewerTestTags.Loading.Root).assertIsDisplayed()
    }

    fun verifyContentDisplayed(): ViewerScreenRobot = apply {
        composeTestRule.onTag(ViewerTestTags.Content.Root).assertIsDisplayed()
    }

    fun verifyTopAppBarDisplayed(): ViewerScreenRobot = apply {
        composeTestRule.onTag(ViewerTestTags.TopAppBar.Root).assertIsDisplayed()
    }

    fun verifyErrorContentDisplayed(): ViewerScreenRobot = apply {
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.Root).assertIsDisplayed()
    }

    fun verifyRetryButtonDisplayed(): ViewerScreenRobot = apply {
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