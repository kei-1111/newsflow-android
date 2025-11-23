package io.github.kei_1111.newsflow.android.core.designsystem.component.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.core.ui.provider.LocalDebounceClicker

@Composable
fun NewsflowTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor,
    interactionSource: MutableInteractionSource? = null,
) {
    val debounceClicker = LocalDebounceClicker.current

    Tab(
        selected = selected,
        onClick = { debounceClicker.processClick(onClick) },
        modifier = modifier,
        enabled = enabled,
        text = text,
        icon = icon,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource,
    )
}

@Composable
@ComponentPreviews
private fun NewsflowTabPreview() {
    NewsflowAndroidTheme {
        var count by remember { mutableIntStateOf(0) }

        Surface {
            NewsflowTab(
                selected = true,
                onClick = { count++ },
                text = { Text("Clicked: $count") },
            )
        }
    }
}
