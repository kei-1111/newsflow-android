@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.kei_1111.newsflow.android.core.ui.provider.DebounceClicker
import io.github.kei_1111.newsflow.android.core.ui.provider.LocalDebounceClicker
import io.github.kei_1111.newsflow.library.core.model.NewsflowError
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w400dp-h800dp-xxhdpi")
class ErrorContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // region UI Tests

    @Test
    fun errorContent_networkError_displaysRetryButton() {
        composeTestRule.setContent {
            TestTheme {
                Surface{
                    ErrorContent(
                        error = NewsflowError.NetworkError.NetworkFailure("Network Error"),
                        onClickAction = {},
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun errorContent_internalError_displaysBackButton() {
        composeTestRule.setContent {
            TestTheme {
                Surface {
                    ErrorContent(
                        error = NewsflowError.InternalError.ArticleNotFound("Article Not Found"),
                        onClickAction = {},
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Go Back").assertIsDisplayed()
    }

    @Test
    fun errorContent_retryButtonTriggersCallback() {
        var clicked = false

        composeTestRule.setContent {
            TestTheme {
                Surface {
                    ErrorContent(
                        error = NewsflowError.NetworkError.NetworkFailure("Network Error"),
                        onClickAction = { clicked = true },
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Retry").performClick()
        assert(clicked) { "Retry button click was not triggered" }
    }

    // endregion

    // region Screenshot Tests

    @Test
    fun errorContent_networkFailure_screenshot() {
        composeTestRule.setContent {
            TestTheme {
                Surface {
                    ErrorContent(
                        error = NewsflowError.NetworkError.NetworkFailure("Network Error"),
                        onClickAction = {},
                    )
                }
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun errorContent_unauthorized_screenshot() {
        composeTestRule.setContent {
            TestTheme {
                Surface {
                    ErrorContent(
                        error = NewsflowError.NetworkError.Unauthorized(),
                        onClickAction = {},
                    )
                }
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun errorContent_serverError_screenshot() {
        composeTestRule.setContent {
            TestTheme {
                Surface {
                    ErrorContent(
                        error = NewsflowError.NetworkError.ServerError("Server Error"),
                        onClickAction = {},
                    )
                }
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun errorContent_articleNotFound_screenshot() {
        composeTestRule.setContent {
            TestTheme {
                Surface {
                    ErrorContent(
                        error = NewsflowError.InternalError.ArticleNotFound("Article Not Found"),
                        onClickAction = {},
                    )
                }
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }

    // endregion
}

/**
 * Robolectric環境用のテストテーマ
 * dynamicColorSchemeはRobolectricで動作しないため固定スキームを使用
 * debounceMillis=0のDebounceClickerを提供してクリックテストを即座に処理
 */
@Composable
private fun TestTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDebounceClicker provides DebounceClicker(debounceMillis = 0L)) {
        MaterialExpressiveTheme(
            colorScheme = lightColorScheme(),
            content = content
        )
    }
}