package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.home.BuildConfig
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import io.github.kei_1111.newsflow.library.feature.home.HomeUiAction
import io.github.kei_1111.newsflow.library.feature.home.HomeUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeContent(
    uiState: HomeUiState.Stable,
    onUiAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        initialPage = NewsCategory.entries.indexOf(uiState.currentNewsCategory),
        pageCount = { NewsCategory.entries.size }
    )
    val coroutineScope = rememberCoroutineScope()

    val currentOnUiAction by rememberUpdatedState(onUiAction)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val layoutDirection = LocalLayoutDirection.current

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .drop(1) // Skip initial value to avoid duplicate event on first render
            .collect { page ->
                currentOnUiAction(HomeUiAction.OnSwipNewsCategoryPage(NewsCategory.entries[page]))
            }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { HomeTopAppBar(scrollBehavior) },
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
                selectedCategory = uiState.currentNewsCategory,
                onClickNewsCategoryTab = { category ->
                    currentOnUiAction(HomeUiAction.OnClickNewsCategoryTag(category))
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(NewsCategory.entries.indexOf(category))
                    }
                },
            )
            HomeHorizontalPager(
                pagerState = pagerState,
                isLoading = uiState.isLoading,
                articlesByCategory = uiState.articlesByCategory,
                onClickArticleCard = { currentOnUiAction(HomeUiAction.OnClickArticleCard(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }
}

@Composable
@ComponentPreviews
private fun HomeContentPreview() {
    NewsflowAndroidTheme {
        Surface {
            HomeContent(
                uiState = HomeUiState.Stable(
                    isLoading = false,
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
                onUiAction = {},
            )
        }
    }
}
