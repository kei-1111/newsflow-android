package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.component.common.NewsflowTab
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.home.R
import io.github.kei_1111.newsflow.library.core.model.NewsCategory

@Composable
internal fun HomeTabRow(
    selectedCategory: NewsCategory,
    onClickNewsCategory: (NewsCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    PrimaryScrollableTabRow(
        selectedTabIndex = NewsCategory.entries.indexOf(selectedCategory),
        modifier = modifier,
        edgePadding = 0.dp,
        minTabWidth = 0.dp,
        contentColor = TabRowDefaults.primaryContentColor
    ) {
        NewsCategory.entries.forEach { newsCategory ->
            NewsCategoryTab(
                newsCategory = newsCategory,
                selected = newsCategory == selectedCategory,
                onClick = { onClickNewsCategory(newsCategory) },
            )
        }
    }
}

@Suppress("CyclomaticComplexMethod")
@Composable
private fun NewsCategoryTab(
    newsCategory: NewsCategory,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NewsflowTab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        text = {
            Text(
                text = stringResource(
                    when (newsCategory) {
                        NewsCategory.GENERAL -> R.string.news_category_general
                        NewsCategory.BUSINESS -> R.string.news_category_business
                        NewsCategory.TECHNOLOGY -> R.string.news_category_technology
                        NewsCategory.ENTERTAINMENT -> R.string.news_category_entertainment
                        NewsCategory.SPORTS -> R.string.news_category_sports
                        NewsCategory.SCIENCE -> R.string.news_category_science
                        NewsCategory.HEALTH -> R.string.news_category_health
                    }
                ),
            )
        },
        icon = {
            Icon(
                painter = painterResource(
                    when (newsCategory) {
                        NewsCategory.GENERAL -> R.drawable.ic_public
                        NewsCategory.BUSINESS -> R.drawable.ic_business_center
                        NewsCategory.TECHNOLOGY -> R.drawable.ic_memory
                        NewsCategory.ENTERTAINMENT -> R.drawable.ic_movie
                        NewsCategory.SPORTS -> R.drawable.ic_sports_soccer
                        NewsCategory.SCIENCE -> R.drawable.ic_science
                        NewsCategory.HEALTH -> R.drawable.ic_health_and_safety
                    }
                ),
                contentDescription = null,
            )
        }
    )
}

@Composable
@ComponentPreviews
private fun HomeTabRowPreview() {
    NewsflowAndroidTheme {
        Surface {
            HomeTabRow(
                selectedCategory = NewsCategory.entries.first(),
                onClickNewsCategory = {},
            )
        }
    }
}

@Composable
@ComponentPreviews
private fun CategoryTabPreview(
    @PreviewParameter(CategoryTabPPP::class) parameter: CategoryTabPreviewParameter
) {
    NewsflowAndroidTheme {
        Surface {
            NewsCategoryTab(
                newsCategory = parameter.newsCategory,
                selected = true,
                onClick = {},
            )
        }
    }
}

private data class CategoryTabPreviewParameter(
    val newsCategory: NewsCategory
)

private class CategoryTabPPP : CollectionPreviewParameterProvider<CategoryTabPreviewParameter>(
    collection = listOf(
        CategoryTabPreviewParameter(NewsCategory.GENERAL),
        CategoryTabPreviewParameter(NewsCategory.BUSINESS),
        CategoryTabPreviewParameter(NewsCategory.TECHNOLOGY),
        CategoryTabPreviewParameter(NewsCategory.ENTERTAINMENT),
        CategoryTabPreviewParameter(NewsCategory.SPORTS),
        CategoryTabPreviewParameter(NewsCategory.SCIENCE),
        CategoryTabPreviewParameter(NewsCategory.HEALTH),
    )
)
