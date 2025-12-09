@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package io.github.kei_1111.newsflow.android.core.test

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import io.github.kei_1111.newsflow.android.core.ui.provider.DebouncedClickProvider

@Suppress("ModifierMissing")
@Composable
fun NewsflowTestTheme(content: @Composable () -> Unit) {
    DebouncedClickProvider {
        MaterialExpressiveTheme(
            colorScheme = lightColorScheme(),
            content = { Surface { content() } }
        )
    }
}
