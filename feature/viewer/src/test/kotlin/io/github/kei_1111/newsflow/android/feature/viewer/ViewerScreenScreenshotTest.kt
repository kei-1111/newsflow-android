package io.github.kei_1111.newsflow.android.feature.viewer

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.kei_1111.newsflow.android.core.test.NewsflowTestRunner
import io.github.kei_1111.newsflow.android.core.test.setNewsflowContent
import io.github.kei_1111.newsflow.android.feature.viewer.fixture.ViewerTestFixtures
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode

@RunWith(NewsflowTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ViewerScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // === Init State Screenshots ===

    @Test
    fun viewerScreen_initState() {
        composeTestRule.setNewsflowContent {
            ViewerScreen(
                state = ViewerTestFixtures.createInitState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Loading State Screenshots ===

    @Test
    fun viewerScreen_loadingState() {
        composeTestRule.setNewsflowContent {
            ViewerScreen(
                state = ViewerTestFixtures.createLoadingState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // === Error State Screenshots ===

    @Test
    fun viewerScreen_errorState_articleNotFound() {
        composeTestRule.setNewsflowContent {
            ViewerScreen(
                state = ViewerTestFixtures.createErrorState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun viewerScreen_errorState_networkFailure() {
        composeTestRule.setNewsflowContent {
            ViewerScreen(
                state = ViewerTestFixtures.createNetworkErrorState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun viewerScreen_errorState_serverError() {
        composeTestRule.setNewsflowContent {
            ViewerScreen(
                state = ViewerTestFixtures.createServerErrorState(),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
