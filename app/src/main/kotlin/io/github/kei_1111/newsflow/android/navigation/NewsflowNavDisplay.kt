package io.github.kei_1111.newsflow.android.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import io.github.kei_1111.newsflow.android.core.navigation.Home
import io.github.kei_1111.newsflow.android.core.navigation.Viewer
import io.github.kei_1111.newsflow.android.feature.home.navigation.homeEntry

@Suppress("ModifierMissing")
@Composable
fun NewsflowNavDisplay() {
    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            homeEntry(navigateViewer = { backStack.add(Viewer("test")) })

            entry<Viewer> {
                Text("Viewer")
            }
        }
    )
}
