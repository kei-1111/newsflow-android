package io.github.kei_1111.newsflow.android.feature.search.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import io.github.kei_1111.newsflow.android.core.designsystem.component.common.NewsflowIconButton
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.search.R
import io.github.kei_1111.newsflow.android.feature.search.SearchTestTags

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun SearchTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    query: String,
    focusRequester: FocusRequester,
    onChangeQuery: (String) -> Unit,
    onClickClear: () -> Unit,
    onClickBack: () -> Unit,
    onClickOption: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val topPadding = lerp(
        start = statusBarHeight,
        stop = 0.dp,
        fraction = scrollBehavior.state.collapsedFraction
    )

    TopAppBar(
        title = {
            SearchTextField(
                query = query,
                onChangeQuery = onChangeQuery,
                onClickClear = onClickClear,
                focusRequester = focusRequester,
            )
        },
        modifier = modifier.testTag(SearchTestTags.TopAppBar.Root),
        navigationIcon = {
            NewsflowIconButton(
                onClick = onClickBack,
                shapes = IconButtonDefaults.shapes(),
                modifier = Modifier.testTag(SearchTestTags.TopAppBar.BackButton)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            NewsflowIconButton(
                onClick = onClickOption,
                shapes = IconButtonDefaults.shapes(),
                modifier = Modifier.testTag(SearchTestTags.TopAppBar.OptionButton)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_option),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        windowInsets = WindowInsets(0.dp, topPadding, 0.dp, 0.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
        ),
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ComponentPreviews
private fun SearchTopAppBarPreview() {
    NewsflowAndroidTheme {
        Surface {
            SearchTopAppBar(
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                query = "",
                onChangeQuery = {},
                onClickClear = {},
                onClickBack = {},
                onClickOption = {},
                focusRequester = remember { FocusRequester() },
            )
        }
    }
}
