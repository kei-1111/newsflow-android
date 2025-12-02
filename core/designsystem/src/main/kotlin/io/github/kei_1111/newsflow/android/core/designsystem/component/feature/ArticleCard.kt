package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ArticleCard(
    article: Article,
    onClickArticle: () -> Unit,
    onClickMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .debouncedClickable { onClickArticle() }
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    article.source?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    article.author?.let {
                        if (article.source != null) {
                            Text(
                                text = "•",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Text(
                    text = article.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = formatDate(article.publishedAt),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            article.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .height(80.dp)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = onClickMore,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Top),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_more_vert),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
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
                onClickArticle = {},
                onClickMore = {},
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

private class ArticleCardPPP : CollectionPreviewParameterProvider<ArticleCardPreviewParameter>(
    collection = listOf(
        ArticleCardPreviewParameter(
            source = "Politico",
            author = "Will Knight",
            description = """
                At its Re:Invent conference, 
                Amazon also announced new tools to help customers build generative AI programs, 
                including one that checks whether a chatbot's outputs are accurate or not.
            """.trimIndent(),
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
