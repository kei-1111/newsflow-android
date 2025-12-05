package io.github.kei_1111.newsflow.android.feature.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.component.common.NewsflowIconButton
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.search.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun SearchTextField(
    query: String,
    focusRequester: FocusRequester,
    onChangeQuery: (String) -> Unit,
    onClickClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .padding(vertical = 8.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = onChangeQuery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true,
                    maxLines = 1
                )
                if (query.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_placeholder),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.24f),
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            NewsflowIconButton(
                onClick = onClickClear,
                shapes = IconButtonDefaults.shapes(),
                modifier = Modifier.size(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_clear),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
@ComponentPreviews
private fun SearchTextFieldPreview(
    @PreviewParameter(SearchTextFieldPPP::class) parameter: SearchTextFieldPreviewParameter
) {
    NewsflowAndroidTheme {
        Surface {
            SearchTextField(
                query = parameter.query,
                onChangeQuery = {},
                onClickClear = {},
                focusRequester = remember { FocusRequester() },
            )
        }
    }
}

private data class SearchTextFieldPreviewParameter(
    val query: String,
)

private class SearchTextFieldPPP : CollectionPreviewParameterProvider<SearchTextFieldPreviewParameter>(
    collection = listOf(
        SearchTextFieldPreviewParameter(
            query = ""
        ),
        SearchTextFieldPreviewParameter(
            query = "kmp"
        )
    )
)
