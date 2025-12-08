package io.github.kei_1111.newsflow.android.core.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag

/**
 * setContentにNewsflowTestThemeを自動適用する拡張関数
 */
fun ComposeContentTestRule.setNewsflowContent(content: @Composable () -> Unit) {
    setContent {
        NewsflowTestTheme {
            content()
        }
    }
}

/**
 * onNodeWithTagの短縮形
 */
fun ComposeContentTestRule.onTag(testTag: String): SemanticsNodeInteraction =
    onNodeWithTag(testTag)