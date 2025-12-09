package io.github.kei_1111.newsflow.android.feature.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.BuildConfig
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ArticleCardList
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ArticleOverviewBottomSheet
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.LoadingContent
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.search.SearchTestTags
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.feature.search.SearchIntent
import io.github.kei_1111.newsflow.library.feature.search.SearchState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    state: SearchState.Stable,
    onIntent: (SearchIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val layoutDirection = LocalLayoutDirection.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    state.selectedArticle?.let {
        ArticleOverviewBottomSheet(
            article = it,
            onDismiss = { onIntent(SearchIntent.DismissArticleOverview) },
            onClickCopyUrl = { onIntent(SearchIntent.CopyArticleUrl) },
            onClickShare = { onIntent(SearchIntent.ShareArticle) },
            onClickSummary = { /* TODO: AIによる記事要約機能を実装する際に作成 */ },
            onClickBookmark = { /* TODO: ブックマーク機能を実装する際に作成 */ },
        )
    }

    if (state.isOptionsSheetVisible) {
        SearchOptionBottomSheet(
            searchOptions = state.searchOptions,
            onChangeSortBy = { onIntent(SearchIntent.UpdateSortBy(it)) },
            onChangeDateRange = { onIntent(SearchIntent.UpdateDateRange(it)) },
            onChangeLanguage = { onIntent(SearchIntent.UpdateLanguage(it)) },
            onDismiss = { onIntent(SearchIntent.DismissOptionsSheet) },
        )
    }

    Scaffold(
        modifier = modifier.testTag(SearchTestTags.Content.Root),
        topBar = {
            SearchTopAppBar(
                scrollBehavior = scrollBehavior,
                query = state.query,
                focusRequester = focusRequester,
                onChangeQuery = { onIntent(SearchIntent.UpdateQuery(it)) },
                onClickClear = { onIntent(SearchIntent.ClearQuery) },
                onClickBack = { onIntent(SearchIntent.NavigateBack) },
                onClickOption = { onIntent(SearchIntent.ShowOptionsSheet) },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                )
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentAlignment = Alignment.Center
        ) {
            if (state.isSearching) {
                LoadingContent(
                    modifier = Modifier.testTag(SearchTestTags.Loading.Root)
                )
            } else {
                ArticleCardList(
                    articles = state.articles,
                    onClickArticle = { onIntent(SearchIntent.NavigateViewer(it)) },
                    onClickMore = { onIntent(SearchIntent.ShowArticleOverview(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(SearchTestTags.ArticleList.Root),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    )
                )
            }
        }
    }
}

@Composable
@ComponentPreviews
private fun SearchContentPreview(
    @PreviewParameter(SearchContentPPP::class) parameter: SearchContentPreviewParameter
) {
    NewsflowAndroidTheme {
        Surface {
            SearchContent(
                state = SearchState.Stable(
                    query = parameter.query,
                    isSearching = parameter.isSearching,
                    articles = parameter.articles,
                    selectedArticle = parameter.selectedArticle,
                ),
                onIntent = {},
            )
        }
    }
}

private data class SearchContentPreviewParameter(
    val query: String,
    val isSearching: Boolean,
    val articles: List<Article>,
    val selectedArticle: Article?
)

private class SearchContentPPP : CollectionPreviewParameterProvider<SearchContentPreviewParameter>(
    collection = listOf(
        SearchContentPreviewParameter(
            query = "",
            isSearching = false,
            articles = emptyList(),
            selectedArticle = null,
        ),
        SearchContentPreviewParameter(
            query = "Amazon",
            isSearching = true,
            articles = emptyList(),
            selectedArticle = null
        ),
        SearchContentPreviewParameter(
            query = "Amazon",
            isSearching = false,
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
            selectedArticle = null,
        ),
        SearchContentPreviewParameter(
            query = "Amazon",
            isSearching = false,
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
            ),
        )
    )
)
