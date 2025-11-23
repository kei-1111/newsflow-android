package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.modifier.debouncedClickable
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.home.BuildConfig
import io.github.kei_1111.newsflow.library.core.model.Article
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ArticleCard(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column(
            modifier = Modifier.debouncedClickable { onClick() }
        ) {
            article.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    article.source?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMediumEmphasized,
                        )
                    }
                    article.author?.let {
                        if (article.source != null) {
                            Text(
                                text = "•",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    minLines = 2,
                    style = MaterialTheme.typography.titleMediumEmphasized,
                )
                article.description?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3,
                        minLines = 3,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = formatDate(article.publishedAt),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    return dateFormat.format(Date(timestamp))
}

@Composable
@ComponentPreviews
private fun ArticleCardPreview(
    @PreviewParameter(ArticleCardPPP::class) parameter: ArticleCardPreviewParameter
) {
    NewsflowAndroidTheme {
        Surface {
            ArticleCard(
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
                onClick = {},
            )
        }
    }
}

private data class ArticleCardPreviewParameter(
    val source: String?,
    val author: String?,
    val description: String?,
    val imageUrl: String?
)

private class ArticleCardPPP : CollectionPreviewParameterProvider<ArticleCardPreviewParameter> (
    collection = listOf(
        ArticleCardPreviewParameter(
            source = "Politico",
            author = "Will Knight",
            description = "At its Re:Invent conference, Amazon also announced new tools to help customers build generative AI programs, including one that checks whether a chatbot's outputs are accurate or not.",
            imageUrl = "${BuildConfig.DRAWABLE_PATH}/img_article_card_preview.png"
        ),
        ArticleCardPreviewParameter(
            source = null,
            author = "Will Knight",
            description = null,
            imageUrl = null,
        ),
        ArticleCardPreviewParameter(
            source = "Politico",
            author = null,
            description = null,
            imageUrl = null
        )
    )
)
