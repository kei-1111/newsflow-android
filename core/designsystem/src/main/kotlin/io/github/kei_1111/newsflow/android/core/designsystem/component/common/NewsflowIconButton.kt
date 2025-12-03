package io.github.kei_1111.newsflow.android.core.designsystem.component.common

import android.R.attr.onClick
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import io.github.kei_1111.newsflow.android.core.designsystem.R
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.core.ui.provider.LocalDebounceClicker

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NewsflowIconButton(
    onClick: () -> Unit,
    shapes: IconButtonShapes,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    val debounceClicker = LocalDebounceClicker.current

    IconButton(
        onClick = { debounceClicker.processClick(onClick) },
        shapes = shapes,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
        content = content
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@ComponentPreviews
private fun NewsflowIconButtonPreview() {
    NewsflowAndroidTheme {
        var count by remember { mutableIntStateOf(0) }

        Surface {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NewsflowIconButton(
                    onClick = { count++ },
                    shapes = IconButtonDefaults.shapes(),
                    content = {
                        Icon(
                            painter = painterResource(R.drawable.ic_share),
                            contentDescription = null,
                        )
                    }
                )
                Text(count.toString())
            }
        }
    }
}
