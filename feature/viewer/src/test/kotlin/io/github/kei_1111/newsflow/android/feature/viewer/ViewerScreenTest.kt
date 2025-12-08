package io.github.kei_1111.newsflow.android.feature.viewer

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34])
class ViewerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun placeholder() {
        // TODO: ViewerScreenのテストを実装
    }
}
