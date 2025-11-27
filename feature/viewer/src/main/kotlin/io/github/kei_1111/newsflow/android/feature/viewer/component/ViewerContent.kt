package io.github.kei_1111.newsflow.android.feature.viewer.component

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.viewinterop.AndroidView
import io.github.kei_1111.newsflow.android.core.designsystem.BuildConfig
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerUiAction
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerUiState

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ViewerContent(
    uiState: ViewerUiState.Stable,
    onUiAction: (ViewerUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ViewerTopAppBar(
                scrollBehavior = scrollBehavior,
                onClickBackButton = { onUiAction(ViewerUiAction.OnClickBackButton) },
                onClickShareButton = { onUiAction(ViewerUiAction.OnClickShareButton(uiState.viewingArticle)) },
                onClickBookmarkButton = { /* TODO: ブックマーク機能を実装する際に作成 */ },
            )
        },
    ) { innerPadding ->
        AndroidView(
            factory = { context ->
                WebView(context).apply {
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
                    webViewClient = WebViewClient()

                    // WebViewのスクロールを監視してTopAppBarの高さと色を制御
                    setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                        val deltaY = (oldScrollY - scrollY).toFloat()
                        scrollBehavior.state.heightOffset += deltaY
                        scrollBehavior.state.contentOffset = -scrollY.toFloat()
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            update = { webView ->
                webView.loadUrl(uiState.viewingArticle.url)
            }
        )
    }
}

@Composable
@ComponentPreviews
private fun ViewerContentPreview() {
    NewsflowAndroidTheme {
        Surface {
            ViewerContent(
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
                    ),
                ),
                onUiAction = {},
            )
        }
    }
}
