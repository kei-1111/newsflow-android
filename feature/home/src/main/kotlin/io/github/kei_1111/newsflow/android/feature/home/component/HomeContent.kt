package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ArticleOverviewBottomSheet
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.home.BuildConfig
import io.github.kei_1111.newsflow.android.feature.home.HomeTestTags
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import io.github.kei_1111.newsflow.library.feature.home.HomeIntent
import io.github.kei_1111.newsflow.library.feature.home.HomeState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeContent(
    state: HomeState.Stable,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        initialPage = NewsCategory.entries.indexOf(state.currentNewsCategory),
        pageCount = { NewsCategory.entries.size }
    )
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pullToRefreshState = rememberPullToRefreshState()

    val layoutDirection = LocalLayoutDirection.current

    LaunchedEffect(pagerState, onIntent) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .drop(1) // Skip initial value to avoid duplicate event on first render
            .collect { page ->
                onIntent(HomeIntent.ChangeCategory(NewsCategory.entries[page]))
            }
    }

    state.selectedArticle?.let {
        ArticleOverviewBottomSheet(
            article = it,
            onDismiss = { onIntent(HomeIntent.DismissArticleOverview) },
            onClickCopyUrl = { onIntent(HomeIntent.CopyArticleUrl) },
            onClickShare = { onIntent(HomeIntent.ShareArticle) },
            onClickSummary = { /* TODO: AIによる記事要約機能を実装する際に作成 */ },
            onClickBookmark = { /* TODO: ブックマーク機能を実装する際に作成 */ },
        )
    }

    Scaffold(
        modifier = modifier.testTag(HomeTestTags.Content.Root),
        topBar = {
            HomeTopAppBar(
                scrollBehavior = scrollBehavior,
                onClickSearch = { onIntent(HomeIntent.NavigateSearch) }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                ),
        ) {
            HomeTabRow(
                selectedCategory = state.currentNewsCategory,
                onClickNewsCategory = { category ->
                    onIntent(HomeIntent.ChangeCategory(category))
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(NewsCategory.entries.indexOf(category))
                    }
                },
            )
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { onIntent(HomeIntent.RefreshArticles) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pullToRefreshState,
            ) {
                HomeHorizontalPager(
                    pagerState = pagerState,
                    isLoading = state.isLoading,
                    articlesByCategory = state.articlesByCategory,
                    onClickArticle = { onIntent(HomeIntent.NavigateViewer(it)) },
                    onClickMore = { onIntent(HomeIntent.ShowArticleOverview(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                )
            }
        }
    }
}

@Composable
@ComponentPreviews
private fun HomeContentPreview(
    @PreviewParameter(HomeContentPPP::class) parameter: HomeContentPreviewParameter,
) {
    NewsflowAndroidTheme {
        Surface {
            HomeContent(
                state = HomeState.Stable(
                    isLoading = false,
                    selectedArticle = parameter.selectedArticle,
                    currentNewsCategory = NewsCategory.GENERAL,
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
                onIntent = {},
            )
        }
    }
}

private data class HomeContentPreviewParameter(
    val selectedArticle: Article?,
)

private class HomeContentPPP : CollectionPreviewParameterProvider<HomeContentPreviewParameter>(
    collection = listOf(
        HomeContentPreviewParameter(
            selectedArticle = null,
        ),
        HomeContentPreviewParameter(
            selectedArticle = Article(
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
        )
    )
)
