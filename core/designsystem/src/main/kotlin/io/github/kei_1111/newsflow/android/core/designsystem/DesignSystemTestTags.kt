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
        fun moreButton(id: String) = "ArticleCardMoreButton$id"
    }

    object ArticleOverviewBottomSheet {
        const val Root = "ArticleOverviewBottomSheet"
        const val Title = "ArticleOverviewBottomSheetTitle"
        const val CopyUrlButton = "ArticleOverviewBottomSheetCopyUrlButton"
        const val ShareButton = "ArticleOverviewBottomSheetShareButton"
        const val SummaryButton = "ArticleOverviewBottomSheetSummaryButton"
        const val BookmarkButton = "ArticleOverviewBottomSheetBookmarkButton"
    }
}
