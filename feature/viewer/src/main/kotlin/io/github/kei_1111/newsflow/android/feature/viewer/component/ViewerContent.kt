package io.github.kei_1111.newsflow.android.feature.viewer.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.viewinterop.AndroidView
import io.github.kei_1111.newsflow.android.core.designsystem.BuildConfig
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.LoadingContent
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerIntent
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerState

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ViewerContent(
    state: ViewerState.Stable,
    onIntent: (ViewerIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ViewerTopAppBar(
                scrollBehavior = scrollBehavior,
                onClickBack = { onIntent(ViewerIntent.NavigateBack) },
                onClickShare = { onIntent(ViewerIntent.ShareArticle(state.viewingArticle)) },
                onClickBookmark = { /* TODO: ブックマーク機能を実装する際に作成 */ },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        loadUrl(state.viewingArticle.url)
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            databaseEnabled = true
                            allowFileAccess = true
                            allowContentAccess = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        }
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                super.onPageStarted(view, url, favicon)
                                onIntent(ViewerIntent.StartWebViewLoading)
                            }

                            override fun onPageCommitVisible(
                                view: WebView?,
                                url: String?
                            ) {
                                super.onPageCommitVisible(view, url)
                                onIntent(ViewerIntent.FinishWebViewLoading)
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)
                                onIntent(ViewerIntent.FinishWebViewLoading)
                            }

                            override fun onReceivedHttpError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                errorResponse: WebResourceResponse?
                            ) {
                                super.onReceivedHttpError(view, request, errorResponse)
                                onIntent(ViewerIntent.FinishWebViewLoading)
                            }
                        }

                        // WebViewのスクロールを監視してTopAppBarの高さと色を制御
                        setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                            val deltaY = (oldScrollY - scrollY).toFloat()
                            // heightOffsetを有効な範囲（heightOffsetLimit〜0f）にクランプ
                            scrollBehavior.state.heightOffset = (scrollBehavior.state.heightOffset + deltaY)
                                .coerceIn(scrollBehavior.state.heightOffsetLimit, 0f)
                            scrollBehavior.state.contentOffset = -scrollY.toFloat()
                        }
                    }
                },
                modifier = Modifier.fillMaxSize(),
            )

            if (state.isWebViewLoading) {
                LoadingContent()
            }
        }
    }
}

@Composable
@ComponentPreviews
private fun ViewerContentPreview(
    @PreviewParameter(ViewerContentPPP::class) parameter: ViewerContentPreviewParameter,
) {
    NewsflowAndroidTheme {
        Surface {
            ViewerContent(
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
                    isWebViewLoading = parameter.isWebViewLoading,
                ),
                onIntent = {},
            )
        }
    }
}

private data class ViewerContentPreviewParameter(
    val isWebViewLoading: Boolean,
)

private class ViewerContentPPP : CollectionPreviewParameterProvider<ViewerContentPreviewParameter>(
    collection = listOf(
        ViewerContentPreviewParameter(
            isWebViewLoading = false,
        ),
        ViewerContentPreviewParameter(
            isWebViewLoading = true,
        ),
    )
)
