package io.github.kei_1111.newsflow.android.feature.viewer.component

import android.provider.SyncStateContract.Helpers.update
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerUiAction
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerUiState

@Composable
internal fun ViewerContent(
    uiState: ViewerUiState.Stable,
    onUiAction: (ViewerUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        AndroidView(
            factory = { WebView(it) },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            update = { webView ->
                webView.settings.javaScriptEnabled = true
                webView.webViewClient = WebViewClient()
                webView.loadUrl(uiState.viewingArticle.url)
            }
        )
    }
}