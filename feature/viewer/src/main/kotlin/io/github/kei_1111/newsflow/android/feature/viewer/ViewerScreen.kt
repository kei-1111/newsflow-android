package io.github.kei_1111.newsflow.android.feature.viewer

import android.content.Intent
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
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerEffect
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerIntent
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerState
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
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val currentNavigateBack by rememberUpdatedState(navigateBack)

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ViewerEffect.NavigateBack -> {
                    currentNavigateBack()
                }

                is ViewerEffect.ShareArticle -> {
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
        state = state,
        onIntent = viewModel::onIntent,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ViewerScreen(
    state: ViewerState,
    onIntent: (ViewerIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is ViewerState.Init, ViewerState.Loading -> {
                    LoadingContent()
                }

                is ViewerState.Stable -> {
                    ViewerContent(
                        state = state,
                        onIntent = onIntent,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is ViewerState.Error -> {
                    ErrorContent(
                        error = state.error,
                        onClickAction = { onIntent(ViewerIntent.NavigateBack) }
                    )
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
            state = parameter.state,
            onIntent = {},
        )
    }
}

private data class ViewerScreenPreviewParameter(
    val state: ViewerState,
)

private class ViewerScreenPPP : CollectionPreviewParameterProvider<ViewerScreenPreviewParameter> (
    collection = listOf(
        ViewerScreenPreviewParameter(
            state = ViewerState.Init,
        ),
        ViewerScreenPreviewParameter(
            state = ViewerState.Loading,
        ),
        ViewerScreenPreviewParameter(
            state = ViewerState.Stable(
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
                ),
                isWebViewLoading = false,
            )
        ),
        ViewerScreenPreviewParameter(
            state = ViewerState.Error(
                error = NewsflowError.InternalError.ArticleNotFound("Article Not Found")
            )
        )
    )
)
