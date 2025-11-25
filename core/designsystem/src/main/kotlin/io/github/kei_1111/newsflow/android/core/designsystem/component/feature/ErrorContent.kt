package io.github.kei_1111.newsflow.android.core.designsystem.component.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import io.github.kei_1111.newsflow.android.core.designsystem.component.common.NewsflowButton
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.preview.ComponentPreviews
import io.github.kei_1111.newsflow.library.core.model.NewsflowError

@Suppress("CyclomaticComplexMethod")
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ErrorContent(
    error: NewsflowError,
    onClickActionButton: () -> Unit,
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
                    is NewsflowError.NetworkError.Unauthorized -> R.drawable.img_unauthorized
                    is NewsflowError.NetworkError.RateLimitExceeded -> R.drawable.img_ratelimitexceeded
                    is NewsflowError.NetworkError.BadRequest -> R.drawable.img_badrequest
                    is NewsflowError.NetworkError.ServerError -> R.drawable.img_servererror
                    is NewsflowError.NetworkError.NetworkFailure -> R.drawable.img_networkfailure
                    is NewsflowError.InternalError.ArticleNotFound -> R.drawable.img_articlenotfound
                    is NewsflowError.InternalError.InvalidParameter -> R.drawable.img_invalidparameter
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
                    is NewsflowError.NetworkError.Unauthorized -> R.string.error_unauthorized_title
                    is NewsflowError.NetworkError.RateLimitExceeded -> R.string.error_rate_limit_exceeded_title
                    is NewsflowError.NetworkError.BadRequest -> R.string.error_bad_request_title
                    is NewsflowError.NetworkError.ServerError -> R.string.error_server_error_title
                    is NewsflowError.NetworkError.NetworkFailure -> R.string.error_network_failure_title
                    is NewsflowError.InternalError.ArticleNotFound -> R.string.error_article_not_found_title
                    is NewsflowError.InternalError.InvalidParameter -> R.string.error_invalid_parameter_title
                }
            ),
            style = MaterialTheme.typography.headlineMediumEmphasized,
            color = MaterialTheme.colorScheme.error,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(
                when (error) {
                    is NewsflowError.NetworkError.Unauthorized -> R.string.error_unauthorized_description
                    is NewsflowError.NetworkError.RateLimitExceeded -> R.string.error_rate_limit_exceeded_description
                    is NewsflowError.NetworkError.BadRequest -> R.string.error_bad_request_description
                    is NewsflowError.NetworkError.ServerError -> R.string.error_server_error_description
                    is NewsflowError.NetworkError.NetworkFailure -> R.string.error_network_failure_description
                    is NewsflowError.InternalError.ArticleNotFound -> R.string.error_article_not_found_description
                    is NewsflowError.InternalError.InvalidParameter -> R.string.error_invalid_parameter_description
                }
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(32.dp))
        NewsflowButton(
            onClick = onClickActionButton,
            modifier = Modifier.fillMaxWidth(),
            shapes = ButtonDefaults.shapes(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
        ) {
            Text(
                text = stringResource(
                    when (error) {
                        is NewsflowError.NetworkError -> R.string.error_retry_button
                        is NewsflowError.InternalError -> R.string.error_back_button
                    }
                )
            )
        }
    }
}

@Composable
@ComponentPreviews
private fun ErrorContentPreview(
    @PreviewParameter(ErrorContentPPP::class) parameter: ErrorContentPreviewParameter
) {
    NewsflowAndroidTheme {
        Surface {
            ErrorContent(
                error = parameter.error,
                onClickActionButton = {},
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
            error = NewsflowError.NetworkError.Unauthorized(),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.NetworkError.RateLimitExceeded(),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.NetworkError.BadRequest(""),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.NetworkError.ServerError(""),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.NetworkError.NetworkFailure(""),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.InternalError.ArticleNotFound(""),
        ),
        ErrorContentPreviewParameter(
            error = NewsflowError.InternalError.InvalidParameter(""),
        ),
    )
)
