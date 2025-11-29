package io.github.kei_1111.newsflow.android.feature.home

import android.content.ClipData
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ErrorContent
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ScreenPreviews
import io.github.kei_1111.newsflow.android.feature.home.component.HomeContent
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import io.github.kei_1111.newsflow.library.core.model.NewsflowError
import io.github.kei_1111.newsflow.library.feature.home.HomeUiAction
import io.github.kei_1111.newsflow.library.feature.home.HomeUiEffect
import io.github.kei_1111.newsflow.library.feature.home.HomeUiState
import io.github.kei_1111.newsflow.library.feature.home.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Suppress("ModifierMissing")
@Composable
fun HomeScreen(
    navigateViewer: (String) -> Unit,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    val currentNavigateViewer by rememberUpdatedState(navigateViewer)

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateViewer -> {
                    currentNavigateViewer(effect.id)
                }
                is HomeUiEffect.CopyUrl -> {
                    coroutineScope.launch {
                        val url = effect.url
                        val clipData = ClipData.newPlainText(url, url)
                        clipboard.setClipEntry(clipData.toClipEntry())
                        Toast.makeText(context, R.string.copy_url, Toast.LENGTH_SHORT).show()
                    }
                }
                is HomeUiEffect.ShareArticle -> {
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
    Surface(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
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
                        onClickActionButton = { onUiAction(HomeUiAction.OnClickRetryButton) }
                    )
                }
            }
        }
    }
}

@Composable
@ScreenPreviews
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPPP::class) parameter: HomeScreenPreviewParameter,
) {
    NewsflowAndroidTheme {
        HomeScreen(
            uiState = parameter.uiState,
            onUiAction = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

private data class HomeScreenPreviewParameter(
    val uiState: HomeUiState,
)

private class HomeScreenPPP : CollectionPreviewParameterProvider<HomeScreenPreviewParameter>(
    collection = listOf(
        HomeScreenPreviewParameter(
            uiState = HomeUiState.Stable(
                isLoading = false,
                currentNewsCategory = NewsCategory.GENERAL,
                articlesByCategory = mapOf(
                    NewsCategory.GENERAL to List(10) {
                        Article(
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
                    }
                )
            ),
        ),
        HomeScreenPreviewParameter(
            uiState = HomeUiState.Error(
                error = NewsflowError.NetworkError.NetworkFailure("Network Failure")
            )
        )
    )
)
