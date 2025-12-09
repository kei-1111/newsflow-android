package io.github.kei_1111.newsflow.android.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import io.github.kei_1111.newsflow.android.core.navigation.Home
import io.github.kei_1111.newsflow.android.feature.home.homeEntry
import io.github.kei_1111.newsflow.android.feature.search.navigateSearch
import io.github.kei_1111.newsflow.android.feature.search.searchEntry
import io.github.kei_1111.newsflow.android.feature.viewer.navigateViewer
import io.github.kei_1111.newsflow.android.feature.viewer.viewerEntry

@Suppress("ModifierMissing")
@Composable
fun NewsflowNavDisplay() {
    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = backStack::removeLastOrNull,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            homeEntry(
                navigateSearch = backStack::navigateSearch,
                navigateViewer = backStack::navigateViewer
            )

            searchEntry(
                navigateBack = backStack::removeLastOrNull,
                navigateViewer = backStack::navigateViewer
            )

            viewerEntry(
                navigateBack = backStack::removeLastOrNull
            )
        }
    )
}
