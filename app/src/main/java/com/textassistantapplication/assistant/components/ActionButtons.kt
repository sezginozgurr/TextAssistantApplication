package com.textassistantapplication.assistant.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.textassistantapplication.R
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme
import com.textassistantapplication.uitoolkit.ActionButtonHeight
import com.textassistantapplication.uitoolkit.IconTextButton
import com.textassistantapplication.uitoolkit.Spacing
import com.textassistantapplication.uitoolkit.ThemedPreview

private const val DisabledContentAlpha = 0.5f

/**
 * Generate and Cancel buttons. The caller decides which icon Generate shows, so this component
 * holds no state-to-UI mapping of its own.
 */
@Composable
internal fun ActionButtons(
    isLoading: Boolean,
    generateIcon: ImageVector,
    onGenerateClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        IconTextButton(
            text = stringResource(R.string.action_generate),
            icon = generateIcon,
            onClick = onGenerateClick,
            modifier = Modifier.weight(1f),
        )
        FilledTonalButton(
            onClick = onCancelClick,
            enabled = isLoading,
            modifier = Modifier
                .weight(1f)
                .height(ActionButtonHeight),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    .copy(alpha = DisabledContentAlpha),
            ),
        ) {
            Text(stringResource(R.string.action_cancel))
        }
    }
}

@ThemedPreview
@Composable
private fun ActionButtonsIdlePreview() {
    TextAssistantApplicationTheme {
        ActionButtons(
            isLoading = false,
            generateIcon = Icons.AutoMirrored.Filled.Send,
            onGenerateClick = {},
            onCancelClick = {},
        )
    }
}

@ThemedPreview
@Composable
private fun ActionButtonsLoadingPreview() {
    TextAssistantApplicationTheme {
        ActionButtons(
            isLoading = true,
            generateIcon = Icons.AutoMirrored.Filled.Send,
            onGenerateClick = {},
            onCancelClick = {},
        )
    }
}

@ThemedPreview
@Composable
private fun ActionButtonsRetryPreview() {
    TextAssistantApplicationTheme {
        ActionButtons(
            isLoading = false,
            generateIcon = Icons.Filled.Refresh,
            onGenerateClick = {},
            onCancelClick = {},
        )
    }
}
