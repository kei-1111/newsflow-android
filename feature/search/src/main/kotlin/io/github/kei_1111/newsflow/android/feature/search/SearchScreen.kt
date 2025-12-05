package io.github.kei_1111.newsflow.android.feature.search

import android.content.ClipData
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import io.github.kei_1111.newsflow.android.core.designsystem.BuildConfig
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.ErrorContent
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ScreenPreviews
import io.github.kei_1111.newsflow.android.feature.search.component.SearchContent
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsflowError
import io.github.kei_1111.newsflow.library.feature.search.SearchEffect
import io.github.kei_1111.newsflow.library.feature.search.SearchIntent
import io.github.kei_1111.newsflow.library.feature.search.SearchState
import io.github.kei_1111.newsflow.library.feature.search.SearchViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Suppress("ModifierMissing")
@Composable
fun SearchScreen(
    navigateBack: () -> Unit,
    navigateViewer: (String) -> Unit,
) {
    val viewModel = koinViewModel<SearchViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    val currentNavigateBack by rememberUpdatedState(navigateBack)
    val currentNavigateViewer by rememberUpdatedState(navigateViewer)

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateViewer -> {
                    currentNavigateViewer(effect.articleId)
                }
                is SearchEffect.NavigateBack -> {
                    currentNavigateBack()
                }
                is SearchEffect.CopyUrl -> {
                    coroutineScope.launch {
                        val url = effect.url
                        val clipData = ClipData.newPlainText(url, url)
                        clipboard.setClipEntry(clipData.toClipEntry())
                    }
                }
                is SearchEffect.ShareArticle -> {
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

    SearchScreen(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SearchScreen(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is SearchState.Stable -> {
                    SearchContent(
                        state = state,
                        onIntent = onIntent,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is SearchState.Error -> {
                    ErrorContent(
                        error = state.error,
                        onClickAction = { onIntent(SearchIntent.RetrySearch) }
                    )
                }
            }
        }
    }
}

@Composable
@ScreenPreviews
private fun SearchScreenPreview(
    @PreviewParameter(SearchScreenPPP::class) parameter: SearchScreenPreviewParameter
) {
    NewsflowAndroidTheme {
        SearchScreen(
            state = parameter.state,
            onIntent = {},
        )
    }
}

private data class SearchScreenPreviewParameter(
    val state: SearchState
)

private class SearchScreenPPP : CollectionPreviewParameterProvider<SearchScreenPreviewParameter>(
    collection = listOf(
        SearchScreenPreviewParameter(
            state = SearchState.Stable(
                query = "Amazon",
                isSearching = false,
                articles = List(10) {
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
                },
                selectedArticle = null,
            )
        ),
        SearchScreenPreviewParameter(
            state = SearchState.Error(
                error = NewsflowError.InternalError.InvalidParameter("Search query cannot be empty")
            )
        )
    ),
)
