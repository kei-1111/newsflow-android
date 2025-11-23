package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.home.BuildConfig
import io.github.kei_1111.newsflow.library.core.model.Article

@Composable
fun ArticleCardList(
    articles: List<Article>,
    onClickArticleCard: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(articles) {
            ArticleCard(
                article = it,
                onClick = { onClickArticleCard(it) }
            )
        }
    }
}

@Composable
@ComponentPreviews
private fun ArticleCardListPreview() {
    NewsflowAndroidTheme {
        Surface {
            ArticleCardList(
                articles = List(10) {
                    Article(
                        id = "2135641799",
                        source = "Politico",
                        author = "Will Knight",
                        title = "Amazon Is Building a Mega AI Supercomputer With Anthropic",
                        description = """
                            At its Re:Invent conference, 
                            Amazon also announced new tools to help customers build generative AI programs, 
                            including one that checks whether a chatbot's outputs are accurate or not.
                        """.trimIndent(),
                        url = "https://www.wired.com/story/amazon-reinvent-anthropic-supercomputer/",
                        imageUrl = "${BuildConfig.DRAWABLE_PATH}/img_article_card_preview.png",
                        publishedAt = 1763726640000,
                    )
                },
                onClickArticleCard = {}
            )
        }
    }
}
