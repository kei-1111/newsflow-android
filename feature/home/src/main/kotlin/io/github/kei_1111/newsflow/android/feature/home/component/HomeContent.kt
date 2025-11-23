package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import io.github.kei_1111.newsflow.library.feature.home.HomeUiAction
import io.github.kei_1111.newsflow.library.feature.home.HomeUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

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

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .drop(1) // Skip initial value to avoid duplicate event on first render
            .collect { page ->
                currentOnUiAction(HomeUiAction.OnSwipNewsCategoryPage(NewsCategory.entries[page]))
            }
    }

    Scaffold(
        topBar = { HomeTopAppBar() },
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
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
                    articlesByCategory = mapOf()
                ),
                onUiAction = {},
            )
        }
    }
}
