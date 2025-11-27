package io.github.kei_1111.newsflow.android.feature.viewer

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ErrorContent
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.LoadingContent
import io.github.kei_1111.newsflow.android.feature.viewer.component.ViewerContent
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerUiAction
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerUiEffect
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerUiState
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Suppress("ModifierMissing")
@Composable
fun ViewerScreen(
    articleId: String,
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<ViewerViewModel>(
        parameters = { parametersOf(articleId) }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val currentNavigateBack by rememberUpdatedState(navigateBack)

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ViewerUiEffect.NavigateBack -> {
                    currentNavigateBack()
                }

                is ViewerUiEffect.ShareArticle -> {
                    val title = effect.title
                    val url = effect.url
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, title)
                        putExtra(Intent.EXTRA_TEXT, "${title}\n$url")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, null))
                }
            }
        }
    }

    ViewerScreen(
        uiState = uiState,
        onUiAction = viewModel::onUiAction,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ViewerScreen(
    uiState: ViewerUiState,
    onUiAction: (ViewerUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (uiState) {
            is ViewerUiState.Init, ViewerUiState.Loading -> {
                LoadingContent()
            }

            is ViewerUiState.Stable -> {
                ViewerContent(
                    uiState = uiState,
                    onUiAction = onUiAction,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is ViewerUiState.Error -> {
                ErrorContent(
                    error = uiState.error,
                    onClickActionButton = { onUiAction(ViewerUiAction.OnClickBackButton) }
                )
            }
        }
    }
}
