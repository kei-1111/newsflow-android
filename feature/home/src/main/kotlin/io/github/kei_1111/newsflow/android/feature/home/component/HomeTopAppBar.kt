package io.github.kei_1111.newsflow.android.feature.home.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.component.common.NewsflowIconButton
import io.github.kei_1111.newsflow.android.core.designsystem.component.feature.NewsflowLogo
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.home.HomeTestTags
import io.github.kei_1111.newsflow.android.feature.home.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onClickSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { NewsflowLogo() },
        modifier = modifier.testTag(HomeTestTags.TopAppBar.Root),
        actions = {
            NewsflowIconButton(
                onClick = onClickSearch,
                modifier = Modifier.testTag(HomeTestTags.TopAppBar.SearchButton),
                shapes = IconButtonDefaults.shapes(),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
        ),
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ComponentPreviews
private fun HomeTopAppBarPreview() {
    NewsflowAndroidTheme {
        Surface {
            HomeTopAppBar(
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                onClickSearch = {}
            )
        }
    }
}
