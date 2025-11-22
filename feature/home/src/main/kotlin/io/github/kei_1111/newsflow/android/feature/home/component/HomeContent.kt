package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.LoadingContent
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import io.github.kei_1111.newsflow.library.feature.home.HomeUiState

@Composable
 internal fun HomeContent(
    uiState: HomeUiState.Stable,
    modifier: Modifier = Modifier,
 ) {
    Scaffold(
        topBar = { HomeTopAppBar() },
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            HomeTabRow(
                selectedCategory = uiState.currentNewsCategory,
                onClickCategoryTab = {},
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                if (uiState.isLoading) {
                    LoadingContent()
                } else {
                    Text(
                        text = uiState.articlesByCategory.values.toString()
                    )
                }
            }
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
            )
        }
    }
 }
