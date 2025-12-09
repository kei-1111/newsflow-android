package io.github.kei_1111.newsflow.android.feature.home

import io.github.kei_1111.newsflow.library.core.model.NewsCategory

object HomeTestTags {
    object HomeScreen {
        const val Root = "HomeScreen"
    }

    object TopAppBar {
        const val Root = "HomeTopAppBar"
        const val SearchButton = "HomeTopAppBarSearchButton"
    }

    object TabRow {
        const val Root = "HomeTabRow"
        fun tab(category: NewsCategory) = "HomeTab${category.name}"
    }

    object Content {
        const val Root = "HomeContent"
    }

    object ArticleList {
        const val Root = "HomeArticleList"
    }

    object Loading {
        const val Root = "HomeLoading"
    }
}
