package io.github.kei_1111.newsflow.android.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ErrorContent
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.LoadingContent
import io.github.kei_1111.newsflow.library.feature.home.HomeUiAction
import io.github.kei_1111.newsflow.library.feature.home.HomeUiState
import io.github.kei_1111.newsflow.library.feature.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Suppress("ModifierMissing")
@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onAction = viewModel::onUiAction,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (uiState) {
            is HomeUiState.Init -> {
                Text("Initialize...")
            }
            is HomeUiState.Loading -> {
                LoadingContent()
            }
            is HomeUiState.Stable -> {
                Text(uiState.articlesByCategory.values.toString())
            }
            is HomeUiState.Error -> {
                ErrorContent(
                    error = uiState.error,
                    onClickRetryButton = { onAction(HomeUiAction.OnClickRetryButton) }
                )
            }
        }
    }
}
