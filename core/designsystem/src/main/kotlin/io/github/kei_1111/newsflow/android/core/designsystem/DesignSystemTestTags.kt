package io.github.kei_1111.newsflow.android.core.designsystem

object DesignSystemTestTags {
    object ErrorContent {
        const val Root = "ErrorContent"
        const val Icon = "ErrorContentIcon"
        const val Title = "ErrorContentTitle"
        const val Description = "ErrorContentDescription"
        const val ActionButton = "ErrorContentActionButton"
    }

    object ArticleCard {
        const val Root = "ArticleCard"
        fun card(id: String) = "ArticleCard$id"
    }
}
