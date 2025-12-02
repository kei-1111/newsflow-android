package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.kei_1111.newsflow.android.core.designsystem.BuildConfig
import io.github.kei_1111.newsflow.android.core.designsystem.R
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.modifier.debouncedClickable
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.library.core.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleOverviewBottomSheet(
    article: Article,
    onDismiss: () -> Unit,
    onClickCopyUrl: () -> Unit,
    onClickShare: () -> Unit,
    onClickSummary: () -> Unit,
    onClickBookmark: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        ) {
            article.imageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Text(
                    text = article.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    article.source?.let { source ->
                        Text(
                            text = source,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                    if (article.source != null && article.author != null) {
                        Text(
                            text = "•",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                    article.author?.let { author ->
                        Text(
                            text = author,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
                article.description?.let { description ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = description,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .debouncedClickable(
                            indication = null,
                            onClick = onClickCopyUrl
                        )
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = article.url,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_content_copy),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalIconButton(
                    onClick = onClickShare,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = null,
                    )
                }
                FilledTonalIconButton(
                    onClick = onClickSummary,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_smart_toy),
                        contentDescription = null,
                    )
                }
                FilledTonalIconButton(
                    onClick = onClickBookmark,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_bookmark),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ComponentPreviews
private fun ArticleDetailBottomSheetPreview(
    @PreviewParameter(ArticleOverviewBottomSheetPPP::class) parameter: ArticleOverviewBottomSheetPreviewParameter
) {
    val sheetState = SheetState(
        skipPartiallyExpanded = true,
        positionalThreshold = { 0f },
        velocityThreshold = { 0f },
        initialValue = SheetValue.Expanded,
    )

    NewsflowAndroidTheme {
        Surface {
            ArticleOverviewBottomSheet(
                article = Article(
                    id = "2135641799",
                    source = parameter.source,
                    author = parameter.author,
                    title = "Amazon Is Building a Mega AI Supercomputer With Anthropic",
                    description = parameter.description,
                    url = "https://www.wired.com/story/amazon-reinvent-anthropic-supercomputer/",
                    imageUrl = parameter.imageUrl,
                    publishedAt = 1763726640000,
                ),
                sheetState = sheetState,
                onDismiss = {},
                onClickCopyUrl = {},
                onClickShare = {},
                onClickSummary = {},
                onClickBookmark = {},
            )
        }
    }
}

private data class ArticleOverviewBottomSheetPreviewParameter(
    val source: String?,
    val author: String?,
    val description: String?,
    val imageUrl: String?
)

private class ArticleOverviewBottomSheetPPP :
    CollectionPreviewParameterProvider<ArticleOverviewBottomSheetPreviewParameter>(
        collection = listOf(
            ArticleOverviewBottomSheetPreviewParameter(
                source = "Politico",
                author = "Will Knight",
                description = """
                    At its Re:Invent conference, 
                    Amazon also announced new tools to help customers build generative AI programs, 
                    including one that checks whether a chatbot's outputs are accurate or not.
                """.trimIndent(),
                imageUrl = "${BuildConfig.DRAWABLE_PATH}/img_article_card_preview.png",
            ),
            ArticleOverviewBottomSheetPreviewParameter(
                source = null,
                author = "Will Knight",
                description = null,
                imageUrl = null,
            ),
            ArticleOverviewBottomSheetPreviewParameter(
                source = "Politico",
                author = null,
                description = null,
                imageUrl = null
            )
        )
    )
