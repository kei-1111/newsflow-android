package io.github.kei_1111.newsflow.android.feature.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.github.kei_1111.newsflow.android.core.navigation.Home

fun EntryProviderScope<NavKey>.homeEntry(
    navigateSearch: () -> Unit,
    navigateViewer: (String) -> Unit,
) {
    entry<Home> {
        HomeScreen(
            navigateSearch = navigateSearch,
            navigateViewer = navigateViewer,
        )
    }
}
