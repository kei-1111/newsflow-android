package io.github.kei_1111.newsflow.android.feature.viewer

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kei_1111.newsflow.android.core.designsystem.BuildConfig
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ErrorContent
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.LoadingContent
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ScreenPreviews
import io.github.kei_1111.newsflow.android.feature.viewer.component.ViewerContent
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsflowError
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
    Surface(modifier = modifier) {
        AnimatedContent(
            targetState = uiState,
            label = "ViewerScreen",
        ) { targetUiState ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                when (targetUiState) {
                    is ViewerUiState.Init, ViewerUiState.Loading -> {
                        LoadingContent()
                    }

                    is ViewerUiState.Stable -> {
                        ViewerContent(
                            uiState = targetUiState,
                            onUiAction = onUiAction,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is ViewerUiState.Error -> {
                        ErrorContent(
                            error = targetUiState.error,
                            onClickActionButton = { onUiAction(ViewerUiAction.OnClickBackButton) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@ScreenPreviews
private fun ViewerScreenPreview(
    @PreviewParameter(ViewerScreenPPP::class) parameter: ViewerScreenPreviewParameter
) {
    NewsflowAndroidTheme {
        ViewerScreen(
            uiState = parameter.uiState,
            onUiAction = {},
        )
    }
}

private data class ViewerScreenPreviewParameter(
    val uiState: ViewerUiState,
)

private class ViewerScreenPPP : CollectionPreviewParameterProvider<ViewerScreenPreviewParameter> (
    collection = listOf(
        ViewerScreenPreviewParameter(
            uiState = ViewerUiState.Init,
        ),
        ViewerScreenPreviewParameter(
            uiState = ViewerUiState.Loading,
        ),
        ViewerScreenPreviewParameter(
            uiState = ViewerUiState.Stable(
                viewingArticle = Article(
                    id = "2135641799",
                    source = "Politico",
                    author = "Will Knight",
                    title = "Amazon Is Building a Mega AI Supercomputer With Anthropic",
                    description = """
                        At its Re:Invent conference, 
                        Amazon also announced new tools to help customers build generative AI programs, 
                        including one that checks whether a chatbot's outputs are accurate or not.
                    """.trimIndent(),
                    url = "https://www.wired.com/story/amazon-reinvent-anthropic-supercomputer/",
                    imageUrl = "${BuildConfig.DRAWABLE_PATH}/img_article_card_preview.png",
                    publishedAt = 1763726640000,
                )
            )
        ),
        ViewerScreenPreviewParameter(
            uiState = ViewerUiState.Error(
                error = NewsflowError.InternalError.ArticleNotFound("Article Not Found")
            )
        )
    )
)
