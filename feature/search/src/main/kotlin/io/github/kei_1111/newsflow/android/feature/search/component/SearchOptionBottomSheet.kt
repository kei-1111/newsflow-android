package io.github.kei_1111.newsflow.android.feature.search.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.component.common.NewsflowFilterChip
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.android.feature.search.R
import io.github.kei_1111.newsflow.android.feature.search.SearchTestTags
import io.github.kei_1111.newsflow.library.feature.search.model.DateRangePreset
import io.github.kei_1111.newsflow.library.feature.search.model.SearchLanguage
import io.github.kei_1111.newsflow.library.feature.search.model.SearchOptions
import io.github.kei_1111.newsflow.library.feature.search.model.SortBy

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun SearchOptionBottomSheet(
    searchOptions: SearchOptions,
    onChangeSortBy: (SortBy) -> Unit,
    onChangeDateRange: (DateRangePreset) -> Unit,
    onChangeLanguage: (SearchLanguage) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 24.dp)
                .navigationBarsPadding()
                .testTag(SearchTestTags.OptionBottomSheet.Root),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            OptionSection(
                title = stringResource(R.string.search_option_sort_by),
                options = SortBy.entries,
                selectedOption = searchOptions.sortBy,
                onChangeOption = onChangeSortBy,
                optionLabelResId = {
                    when (it) {
                        SortBy.RELEVANCY -> R.string.search_option_sort_relevancy
                        SortBy.POPULARITY -> R.string.search_option_sort_popularity
                        SortBy.PUBLISHED_AT -> R.string.search_option_sort_newest
                    }
                },
            )

            OptionSection(
                title = stringResource(R.string.search_option_date_range),
                options = DateRangePreset.entries,
                selectedOption = searchOptions.dateRangePreset,
                onChangeOption = onChangeDateRange,
                optionLabelResId = {
                    when (it) {
                        DateRangePreset.ALL -> R.string.search_option_date_all
                        DateRangePreset.LAST_24_HOURS -> R.string.search_option_date_last_24_hours
                        DateRangePreset.LAST_WEEK -> R.string.search_option_date_last_week
                        DateRangePreset.LAST_MONTH -> R.string.search_option_date_last_month
                    }
                },
            )

            OptionSection(
                title = stringResource(R.string.search_option_language),
                options = SearchLanguage.entries,
                selectedOption = searchOptions.language,
                onChangeOption = onChangeLanguage,
                optionLabelResId = {
                    when (it) {
                        SearchLanguage.ALL -> R.string.search_option_language_all
                        SearchLanguage.ENGLISH -> R.string.search_option_language_english
                        SearchLanguage.JAPANESE -> R.string.search_option_language_japanese
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun <T> OptionSection(
    title: String,
    options: List<T>,
    selectedOption: T,
    onChangeOption: (T) -> Unit,
    optionLabelResId: (T) -> Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            options.forEach { option ->
                NewsflowFilterChip(
                    selected = option == selectedOption,
                    onClick = { onChangeOption(option) },
                    label = { Text(stringResource(optionLabelResId(option))) },
                    leadingIcon = {
                        AnimatedVisibility(
                            visible = option == selectedOption
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ComponentPreviews
private fun SearchOptionBottomSheetPreview() {
    val sheetState = SheetState(
        skipPartiallyExpanded = true,
        positionalThreshold = { 0f },
        velocityThreshold = { 0f },
        initialValue = SheetValue.Expanded,
    )

    NewsflowAndroidTheme {
        Surface {
            SearchOptionBottomSheet(
                searchOptions = SearchOptions(),
                onChangeSortBy = {},
                onChangeDateRange = {},
                onChangeLanguage = {},
                onDismiss = {},
                sheetState = sheetState,
            )
        }
    }
}
