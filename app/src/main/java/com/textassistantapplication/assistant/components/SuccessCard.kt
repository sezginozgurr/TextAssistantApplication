package com.textassistantapplication.assistant.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import com.textassistantapplication.R
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme
import com.textassistantapplication.uitoolkit.IconTextButton
import com.textassistantapplication.uitoolkit.Spacing
import com.textassistantapplication.uitoolkit.StatusBadge
import com.textassistantapplication.uitoolkit.StatusCard
import com.textassistantapplication.uitoolkit.ThemedPreview

@Composable
internal fun SuccessCard(
    result: String,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    StatusCard(
        modifier = modifier.semantics { liveRegion = LiveRegionMode.Polite },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        borderColor = MaterialTheme.colorScheme.primary,
    ) {
        StatusBadge(text = stringResource(R.string.success_badge), icon = Icons.Filled.Check)
        Spacer(Modifier.height(Spacing.Medium))
        SelectionContainer {
            Text(text = result, style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(Spacing.Large))
        IconTextButton(
            text = stringResource(R.string.action_share),
            icon = Icons.Filled.Share,
            onClick = onShareClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
        )
    }
}

@ThemedPreview
@Composable
private fun SuccessCardPreview() {
    TextAssistantApplicationTheme {
        SuccessCard(result = "Result: hello", onShareClick = {})
    }
}
