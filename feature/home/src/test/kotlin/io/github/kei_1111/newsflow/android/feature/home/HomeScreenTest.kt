package io.github.kei_1111.newsflow.android.feature.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import io.github.kei_1111.newsflow.android.core.designsystem.DesignSystemTestTags
import io.github.kei_1111.newsflow.android.core.test.onTag
import io.github.kei_1111.newsflow.android.core.test.setNewsflowContent
import io.github.kei_1111.newsflow.library.core.model.NewsflowError
import io.github.kei_1111.newsflow.library.feature.home.HomeIntent
import io.github.kei_1111.newsflow.library.feature.home.HomeState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.shadows.ShadowSystemClock

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34])
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorState_displaysErrorContent() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeState.Error(
                    error = NewsflowError.NetworkError.NetworkFailure("error")
                ),
                onIntent = {},
            )
        }

        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.Root).assertIsDisplayed()
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.ActionButton).assertIsDisplayed()
    }

    @Test
    fun errorState_retryButtonClick_emitsRetryLoadIntent() {
        var receivedIntent: HomeIntent? = null

        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeState.Error(
                    error = NewsflowError.NetworkError.NetworkFailure("error")
                ),
                onIntent = { receivedIntent = it },
            )
        }

        // debounce時間（500ms）を経過させてからクリック
        ShadowSystemClock.advanceBy(java.time.Duration.ofMillis(600))
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.ActionButton).performClick()
        assertEquals(HomeIntent.RetryLoad, receivedIntent)
    }
}
