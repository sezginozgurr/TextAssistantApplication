package com.textassistantapplication.assistant.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import com.textassistantapplication.R
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme
import com.textassistantapplication.uitoolkit.Spacing
import com.textassistantapplication.uitoolkit.StatusCard
import com.textassistantapplication.uitoolkit.ThemedPreview

private val LoadingIndicatorSize = 32.dp
private val LoadingIndicatorStrokeWidth = 3.dp

@Composable
internal fun LoadingCard(modifier: Modifier = Modifier) {
    StatusCard(
        modifier = modifier.semantics { liveRegion = LiveRegionMode.Polite },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        borderColor = MaterialTheme.colorScheme.outline,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(Spacing.Medium))
        CircularProgressIndicator(
            modifier = Modifier.size(LoadingIndicatorSize),
            color = MaterialTheme.colorScheme.secondary,
            strokeWidth = LoadingIndicatorStrokeWidth,
        )
        Spacer(Modifier.height(Spacing.Large))
        Text(
            text = stringResource(R.string.loading_title),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(Spacing.XSmall))
        Text(
            text = stringResource(R.string.loading_hint),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.Medium))
    }
}

@ThemedPreview
@Composable
private fun LoadingCardPreview() {
    TextAssistantApplicationTheme {
        LoadingCard()
    }
}
