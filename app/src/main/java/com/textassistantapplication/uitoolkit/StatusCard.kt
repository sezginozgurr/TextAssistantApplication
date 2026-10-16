package com.textassistantapplication.uitoolkit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme

private val StatusCardBorderWidth = 1.dp

/** Full-width bordered card used to present a single status (loading, result, error). */
@Composable
fun StatusCard(
    containerColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = containerColor,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(StatusCardBorderWidth, borderColor),
    ) {
        Column(
            modifier = Modifier.padding(Spacing.XLarge),
            horizontalAlignment = horizontalAlignment,
            content = content,
        )
    }
}

@ThemedPreview
@Composable
private fun StatusCardPreview() {
    TextAssistantApplicationTheme {
        StatusCard(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            borderColor = MaterialTheme.colorScheme.outline,
        ) {
            Text("Status card content")
        }
    }
}
