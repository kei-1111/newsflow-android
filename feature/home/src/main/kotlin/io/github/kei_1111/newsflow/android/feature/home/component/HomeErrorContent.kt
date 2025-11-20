package io.github.kei_1111.newsflow.android.feature.home.component

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.PreviewComponent
import io.github.kei_1111.newsflow.android.feature.home.R
import io.github.kei_1111.newsflow.library.feature.home.HomeUiState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun HomeErrorContent(
    uiState: HomeUiState.Error,
    onClickRetryButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = uiState.message,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Button(
            onClick = onClickRetryButton,
            shapes = ButtonDefaults.shapes(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            contentPadding = PaddingValues(24.dp, 8.dp)
        ) {
            Text(text = "Retry")
        }
    }
}

@Composable
@PreviewComponent
private fun HomeErrorContentPreview() {
    NewsflowAndroidTheme {
        Surface {
            HomeErrorContent(
                uiState = HomeUiState.Error("エラーが発生しました"),
                onClickRetryButton = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
