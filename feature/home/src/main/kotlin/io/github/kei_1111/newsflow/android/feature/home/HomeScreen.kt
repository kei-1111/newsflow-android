package io.github.kei_1111.newsflow.android.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ErrorContent
import io.github.kei_1111.newsflow.android.feature.home.component.HomeContent
import io.github.kei_1111.newsflow.library.feature.home.HomeUiAction
import io.github.kei_1111.newsflow.library.feature.home.HomeUiEffect
import io.github.kei_1111.newsflow.library.feature.home.HomeUiState
import io.github.kei_1111.newsflow.library.feature.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Suppress("ModifierMissing")
@Composable
fun HomeScreen(
    navigateViewer: () -> Unit,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val currentNavigateViewer by rememberUpdatedState(navigateViewer)

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateViewer -> currentNavigateViewer()
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        onUiAction = viewModel::onUiAction,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onUiAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (uiState) {
            is HomeUiState.Stable -> {
                HomeContent(
                    uiState = uiState,
                    onUiAction = onUiAction,
                )
            }
            is HomeUiState.Error -> {
                ErrorContent(
                    error = uiState.error,
                    onClickRetryButton = { onUiAction(HomeUiAction.OnClickRetryButton) }
                )
            }
        }
    }
}
