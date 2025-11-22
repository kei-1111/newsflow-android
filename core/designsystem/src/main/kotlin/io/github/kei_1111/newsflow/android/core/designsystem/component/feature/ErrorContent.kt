package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.kei_1111.newsflow.android.core.designsystem.R
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.PreviewComponent
import io.github.kei_1111.newsflow.library.core.model.NewsflowError

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ErrorContent(
    error: NewsflowError,
    onClickRetryButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(
                when (error) {
                    is NewsflowError.Unauthorized -> R.drawable.ic_unauthorized
                    is NewsflowError.RateLimitExceeded -> R.drawable.ic_ratelimitexceeded
                    is NewsflowError.BadRequest -> R.drawable.ic_badrequest
                    is NewsflowError.ServerError -> R.drawable.ic_servererror
                    is NewsflowError.NetworkFailure -> R.drawable.ic_networkfailure
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.error,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(
                when (error) {
                    is NewsflowError.Unauthorized -> R.string.error_unauthorized_title
                    is NewsflowError.RateLimitExceeded -> R.string.error_rate_limit_exceeded_title
                    is NewsflowError.BadRequest -> R.string.error_bad_request_title
                    is NewsflowError.ServerError -> R.string.error_server_error_title
                    is NewsflowError.NetworkFailure -> R.string.error_network_failure_title
                }
            ),
            style = MaterialTheme.typography.headlineMediumEmphasized,
            color = MaterialTheme.colorScheme.error,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(
                when (error) {
                    is NewsflowError.Unauthorized -> R.string.error_unauthorized_description
                    is NewsflowError.RateLimitExceeded -> R.string.error_rate_limit_exceeded_description
                    is NewsflowError.BadRequest -> R.string.error_bad_request_description
                    is NewsflowError.ServerError -> R.string.error_server_error_description
                    is NewsflowError.NetworkFailure -> R.string.error_network_failure_description
                }
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onClickRetryButton,
            modifier = Modifier.fillMaxWidth(),
            shapes = ButtonDefaults.shapes(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
        ) {
            Text(text = stringResource(R.string.error_retry_button))
        }
    }
}

@Composable
@PreviewComponent
private fun HomeErrorContentPreview(
    @PreviewParameter(ErrorContentPPP::class) parameter: ErrorContentPreviewParameter
) {
    NewsflowAndroidTheme {
        Surface {
            ErrorContent(
                error = parameter.error,
                onClickRetryButton = {},
            )
        }
    }
}

private data class ErrorContentPreviewParameter(
    val error: NewsflowError,
)

private class ErrorContentPPP : CollectionPreviewParameterProvider<ErrorContentPreviewParameter>(
    collection = listOf(
        ErrorContentPreviewParameter(
            error = NewsflowError.Unauthorized(),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.RateLimitExceeded(),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.BadRequest(""),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.ServerError(""),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.NetworkFailure(""),
        ),
    )
)
