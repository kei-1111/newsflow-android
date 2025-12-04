package io.github.kei_1111.newsflow.android.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.kei_1111.newsflow.android.core.designsystem.component.common.NewsflowButton

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Suppress("ModifierMissing")
@Composable
fun SearchScreen(
    navigateBack: () -> Unit,
    navigateViewer: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NewsflowButton(
            onClick = navigateBack,
            shapes = ButtonDefaults.shapes()
        ) {
            Text("navigate back")
        }

        NewsflowButton(
            onClick = { navigateViewer("") },
            shapes = ButtonDefaults.shapes()
        ) {
            Text("navigate viewer")
        }
    }
}