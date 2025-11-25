package io.github.kei_1111.newsflow.android.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import io.github.kei_1111.newsflow.android.core.navigation.Home
import io.github.kei_1111.newsflow.android.feature.home.navigation.homeEntry
import io.github.kei_1111.newsflow.android.feature.viewer.navigation.navigateViewer
import io.github.kei_1111.newsflow.android.feature.viewer.navigation.viewerEntry

@Suppress("ModifierMissing")
@Composable
fun NewsflowNavDisplay() {
    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            homeEntry(
                navigateViewer = { backStack.navigateViewer(it) }
            )

            viewerEntry(
                navigateBack = { backStack.removeLastOrNull() }
            )
        }
    )
}
