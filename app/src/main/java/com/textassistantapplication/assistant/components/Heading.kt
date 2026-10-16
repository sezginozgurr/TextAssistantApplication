package com.textassistantapplication.assistant.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.textassistantapplication.R
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme
import com.textassistantapplication.uitoolkit.Spacing
import com.textassistantapplication.uitoolkit.ThemedPreview

@Composable
internal fun Heading(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.XSmall)) {
        Text(
            text = stringResource(R.string.title),
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = stringResource(R.string.subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@ThemedPreview
@Composable
private fun HeadingPreview() {
    TextAssistantApplicationTheme {
        Heading()
    }
}
