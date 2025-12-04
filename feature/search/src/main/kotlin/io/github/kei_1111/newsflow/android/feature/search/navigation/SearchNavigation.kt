package io.github.kei_1111.newsflow.android.feature.search.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.kei_1111.newsflow.android.core.navigation.Search
import io.github.kei_1111.newsflow.android.feature.search.SearchScreen

fun NavBackStack<NavKey>.navigateSearch() {
    this.add(Search)
}

fun EntryProviderScope<NavKey>.searchEntry(
    navigateBack: () -> Unit,
    navigateViewer: (String) -> Unit
) {
    entry<Search> {
        SearchScreen(
            navigateBack = navigateBack,
            navigateViewer = navigateViewer,
        )
    }
}