package io.github.kei_1111.newsflow.android.feature.viewer.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.viewer.R

// TODO: nestedScrollが上手く行っておらずWebViewのスクロールで
//  ViewerTopAppBarが閉じるときにガタガタするのを直したい、大きくスクロールすれば出てこないし緊急性は低いので今後やる
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ViewerTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onClickBack: () -> Unit,
    onClickShare: () -> Unit,
    onClickBookmark: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { /*ViewerTopAppBarはtitleに何も表示させないため空*/ },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onClickBack,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClickShare,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    onClick = onClickBookmark,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_bookmark_outlined),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ComponentPreviews
private fun ViewerTopAppBarPreview() {
    NewsflowAndroidTheme {
        Surface {
            ViewerTopAppBar(
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                onClickBack = {},
                onClickShare = {},
                onClickBookmark = {},
            )
        }
    }
}
