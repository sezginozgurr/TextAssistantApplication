package com.textassistantapplication.assistant.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.textassistantapplication.R
import com.textassistantapplication.assistant.TextAssistantError
import com.textassistantapplication.assistant.preview.TextAssistantErrorPreviewProvider
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme
import com.textassistantapplication.uitoolkit.Spacing
import com.textassistantapplication.uitoolkit.StatusCard
import com.textassistantapplication.uitoolkit.ThemedPreview

private val ErrorIconSize = 20.dp

@Composable
internal fun ErrorCard(
    error: TextAssistantError,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.Medium)) {
        StatusCard(
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            containerColor = MaterialTheme.colorScheme.errorContainer,
            borderColor = MaterialTheme.colorScheme.error,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(ErrorIconSize),
                )
                Text(
                    text = stringResource(R.string.error_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            Spacer(Modifier.height(Spacing.Small))
            Text(
                text = stringResource(error.messageRes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
        }
        Text(
            text = stringResource(R.string.error_hint),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@get:StringRes
private val TextAssistantError.messageRes: Int
    get() = when (this) {
        TextAssistantError.ServiceUnavailable -> R.string.error_service_unavailable
        TextAssistantError.Unknown -> R.string.error_unknown
    }

@ThemedPreview
@Composable
private fun ErrorCardPreview(
    @PreviewParameter(TextAssistantErrorPreviewProvider::class) error: TextAssistantError,
) {
    TextAssistantApplicationTheme {
        ErrorCard(error = error)
    }
}
