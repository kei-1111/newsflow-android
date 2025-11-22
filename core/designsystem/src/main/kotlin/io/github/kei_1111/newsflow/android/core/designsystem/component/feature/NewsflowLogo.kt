package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kei_1111.newsflow.android.core.designsystem.R
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.PreviewComponent

@Suppress("ModifierMissing")
@Composable
fun NewsflowLogo() {
    CompositionLocalProvider(
        LocalDensity provides Density(LocalDensity.current.density, 1f)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_appicon),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "NEWSFLOW",
                modifier = Modifier.offset(y = (-2).dp),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.zen_kaku_gothic_new_bold)),
                letterSpacing = (-1).sp,
            )
        }
    }
}

@Composable
@PreviewComponent
private fun NewsflowLogoPreview() {
    NewsflowAndroidTheme {
        Surface {
            NewsflowLogo()
        }
    }
}
