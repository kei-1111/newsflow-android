package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import io.github.kei_1111.newsflow.android.core.designsystem.DesignSystemTestTags
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleSummaryBottomSheet(
    summary: String,
    isSummarizing: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier.testTag(DesignSystemTestTags.ArticleSummaryBottomSheet.Root),
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow,
                    )
                )
                .padding(top = 16.dp)
                .padding(bottom = 24.dp)
                .padding(horizontal = 24.dp),
        ) {
            Text(
                text = "Article Summary",
                modifier = Modifier.testTag(DesignSystemTestTags.ArticleSummaryBottomSheet.Title),
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (summary.isNotEmpty()) {
                Text(
                    text = summary,
                    modifier = Modifier.testTag(DesignSystemTestTags.ArticleSummaryBottomSheet.Summary),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (isSummarizing) {
                LoadingContent(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .testTag(DesignSystemTestTags.ArticleSummaryBottomSheet.Loading)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ComponentPreviews
private fun ArticleSummaryBottomSheetPreview(
    @PreviewParameter(ArticleSummaryBottomSheetPPP::class) parameter: ArticleSummaryBottomSheetPreviewParameter
) {
    val sheetState = SheetState(
        skipPartiallyExpanded = true,
        positionalThreshold = { 0f },
        velocityThreshold = { 0f },
        initialValue = SheetValue.Expanded,
    )

    NewsflowAndroidTheme {
        Surface {
            ArticleSummaryBottomSheet(
                summary = parameter.summary,
                isSummarizing = parameter.isSummarizing,
                onDismiss = {},
                sheetState = sheetState,
            )
        }
    }
}

private data class ArticleSummaryBottomSheetPreviewParameter(
    val summary: String,
    val isSummarizing: Boolean
)

private class ArticleSummaryBottomSheetPPP :
    CollectionPreviewParameterProvider<ArticleSummaryBottomSheetPreviewParameter>(
        collection = listOf(
            ArticleSummaryBottomSheetPreviewParameter(
                summary = "",
                isSummarizing = true
            ),
            ArticleSummaryBottomSheetPreviewParameter(
                summary = """
                    NASA has completed the assembly of its next-generation Nancy Grace Roman Telescope, an infrared observatory slated for launch as early as Fall 2026 or May 2027. 
                    This powerful telescope features a Wide-Field Instrument, providing a view 100 times larger than Hubble's, and an advanced Coronagraph Instrument
                """.trimIndent(),
                isSummarizing = true
            ),
            ArticleSummaryBottomSheetPreviewParameter(
                summary = """
                    NASA has completed the assembly of its next-generation Nancy Grace Roman Telescope, an infrared observatory slated for launch as early as Fall 2026 or May 2027. 
                    This powerful telescope features a Wide-Field Instrument, providing a view 100 times larger than Hubble's, and an advanced Coronagraph Instrument designed to directly image exoplanets by blocking starlight. 
                    The Roman Telescope's primary objectives include studying dark energy, conducting an exoplanet census, detecting primordial black holes, and directly imaging nearby exoplanets. 
                    Expected to uncover over 100,000 distant worlds and billions of galaxies within its five-year mission, it will generate an astounding 20,000 terabytes of data. 
                    Scientists believe the mission will significantly expand our understanding of the Universe, including the accelerating expansion caused by dark energy, and aid in the search for habitable exoplanets.
                """.trimIndent(),
                isSummarizing = false
            )
        )
    )
