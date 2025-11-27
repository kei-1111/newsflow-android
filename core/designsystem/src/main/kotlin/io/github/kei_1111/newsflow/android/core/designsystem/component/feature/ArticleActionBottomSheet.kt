package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.R
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.modifier.debouncedClickable
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleActionBottomSheet(
    onDismissArticleActionBottomSheet: () -> Unit,
    onClickOverviewActionItem: () -> Unit,
    onClickShareActionItem: () -> Unit,
    onClickGeminiSummaryActionItem: () -> Unit,
    onClickBookmarkActionItem: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = onDismissArticleActionBottomSheet,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ActionItem(
                icon = painterResource(R.drawable.ic_overview),
                label = stringResource(R.string.article_action_view_overview),
                onClick = onClickOverviewActionItem,
            )
            ActionItem(
                icon = painterResource(R.drawable.ic_gemini),
                label = stringResource(R.string.article_action_share),
                onClick = onClickShareActionItem,
                iconTint = Color.Unspecified,
            )
            ActionItem(
                icon = painterResource(R.drawable.ic_content_copy),
                label = stringResource(R.string.article_action_summarize_with_gemini),
                onClick = onClickGeminiSummaryActionItem,
            )
            ActionItem(
                icon = painterResource(R.drawable.ic_bookmark),
                label = stringResource(R.string.article_action_bookmark),
                onClick = onClickBookmarkActionItem,
            )
        }
    }
}

@Composable
private fun ActionItem(
    icon: Painter,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .debouncedClickable(
                indication = null,
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconTint,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ComponentPreviews
private fun ArticleActionBottomSheetPreview() {
    val sheetState = SheetState(
        skipPartiallyExpanded = true,
        positionalThreshold = { 0f },
        velocityThreshold = { 0f },
        initialValue = SheetValue.Expanded,
    )

    NewsflowAndroidTheme {
        Surface {
            ArticleActionBottomSheet(
                sheetState = sheetState,
                onDismissArticleActionBottomSheet = {},
                onClickOverviewActionItem = {},
                onClickShareActionItem = {},
                onClickGeminiSummaryActionItem = {},
                onClickBookmarkActionItem = {},
            )
        }
    }
}

@Composable
@ComponentPreviews
private fun ActionItemPreview() {
    NewsflowAndroidTheme {
        Surface {
            ActionItem(
                icon = painterResource(R.drawable.ic_overview),
                label = stringResource(R.string.article_action_view_overview),
                onClick = {},
            )
        }
    }
}