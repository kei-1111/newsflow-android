package io.github.kei_1111.newsflow.android.core.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag

fun ComposeContentTestRule.setNewsflowContent(content: @Composable () -> Unit) {
    setContent {
        NewsflowTestTheme {
            content()
        }
    }
}

fun ComposeContentTestRule.onTag(testTag: String): SemanticsNodeInteraction =
    onNodeWithTag(testTag)
