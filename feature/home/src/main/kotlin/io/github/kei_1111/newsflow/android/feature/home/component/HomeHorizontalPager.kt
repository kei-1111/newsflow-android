package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ArticleCardList
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.LoadingContent
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.home.BuildConfig
import io.github.kei_1111.newsflow.android.feature.home.HomeTestTags
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsCategory

@Composable
internal fun HomeHorizontalPager(
    pagerState: PagerState,
    isLoading: Boolean,
    articlesByCategory: Map<NewsCategory, List<Article>>,
    onClickArticle: (Article) -> Unit,
    onClickMore: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
    ) { page ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (isLoading) {
                LoadingContent(
                    modifier = Modifier.testTag(HomeTestTags.Loading.Root)
                )
            } else {
                ArticleCardList(
                    articles = articlesByCategory[NewsCategory.entries[page]] ?: emptyList(),
                    onClickArticle = onClickArticle,
                    onClickMore = onClickMore,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(HomeTestTags.ArticleList.Root),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                    )
                )
            }
        }
    }
}

@Composable
@ComponentPreviews
private fun HomeHorizontalPagerPreview(
    @PreviewParameter(HomeHorizontalPagerPPP::class) parameter: HomeHorizontalPagerPreviewParameter,
) {
    NewsflowAndroidTheme {
        Surface {
            HomeHorizontalPager(
                pagerState = rememberPagerState { NewsCategory.entries.size },
                isLoading = parameter.isLoading,
                articlesByCategory = parameter.articlesByCategory,
                onClickArticle = {},
                onClickMore = {},
            )
        }
    }
}

private data class HomeHorizontalPagerPreviewParameter(
    val isLoading: Boolean,
    val articlesByCategory: Map<NewsCategory, List<Article>>,
)

private class HomeHorizontalPagerPPP : CollectionPreviewParameterProvider<HomeHorizontalPagerPreviewParameter>(
    collection = listOf(
        HomeHorizontalPagerPreviewParameter(
            isLoading = false,
            articlesByCategory = mapOf(
                NewsCategory.GENERAL to List(10) {
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
                }
            )
        ),
        HomeHorizontalPagerPreviewParameter(
            isLoading = true,
            articlesByCategory = emptyMap()
        )
    )
)
