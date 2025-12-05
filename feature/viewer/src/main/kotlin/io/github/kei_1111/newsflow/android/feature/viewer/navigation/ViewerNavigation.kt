package io.github.kei_1111.newsflow.android.feature.viewer.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.kei_1111.newsflow.android.core.navigation.Viewer
import io.github.kei_1111.newsflow.android.feature.viewer.ViewerScreen

fun NavBackStack<NavKey>.navigateViewer(articleId: String) = this.add(Viewer(articleId))

fun EntryProviderScope<NavKey>.viewerEntry(
    navigateBack: () -> Unit,
) {
    entry<Viewer> { viewer ->
        ViewerScreen(
            articleId = viewer.articleId,
            navigateBack = navigateBack
        )
    }
}
