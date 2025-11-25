package io.github.kei_1111.newsflow.android.feature.home.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.github.kei_1111.newsflow.android.core.navigation.Home
import io.github.kei_1111.newsflow.android.feature.home.HomeScreen

fun EntryProviderScope<NavKey>.homeEntry(
    navigateViewer: (String) -> Unit,
) {
    entry<Home> {
        HomeScreen(
            navigateViewer = navigateViewer,
        )
    }
}
