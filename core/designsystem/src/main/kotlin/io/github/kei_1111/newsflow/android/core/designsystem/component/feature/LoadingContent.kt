package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.PreviewComponent

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    ContainedLoadingIndicator(
        modifier = modifier,
    )
}

@Composable
@PreviewComponent
private fun LoadingContentPreview() {
    NewsflowAndroidTheme {
        LoadingContent()
    }
}
