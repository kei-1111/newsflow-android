@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package io.github.kei_1111.newsflow.android.core.test

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import io.github.kei_1111.newsflow.android.core.ui.provider.DebounceClicker
import io.github.kei_1111.newsflow.android.core.ui.provider.DebouncedClickProvider
import io.github.kei_1111.newsflow.android.core.ui.provider.LocalDebounceClicker

/**
 * Robolectric環境用のテストテーマ
 *
 * - dynamicColorSchemeはRobolectricで動作しないため固定スキームを使用
 * - debounceMillis=0のDebounceClickerを提供してクリックを即座に処理
 */
@Composable
fun NewsflowTestTheme(content: @Composable () -> Unit) {
    DebouncedClickProvider {
        MaterialExpressiveTheme(
            colorScheme = lightColorScheme(),
            content = { Surface { content() } }
        )
    }
}