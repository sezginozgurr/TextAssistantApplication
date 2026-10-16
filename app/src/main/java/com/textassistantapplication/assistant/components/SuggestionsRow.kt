package com.textassistantapplication.assistant.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.textassistantapplication.R
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme
import com.textassistantapplication.uitoolkit.Spacing
import com.textassistantapplication.uitoolkit.ThemedPreview

private val SuggestionChipBorderWidth = 1.dp

@Composable
internal fun SuggestionsRow(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
        Text(
            text = stringResource(R.string.suggestions_label),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Small)) {
            suggestions.forEach { suggestion ->
                SuggestionChip(
                    onClick = { onSuggestionClick(suggestion) },
                    label = { Text(suggestion) },
                    shape = CircleShape,
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    border = BorderStroke(
                        SuggestionChipBorderWidth,
                        MaterialTheme.colorScheme.outline,
                    ),
                )
            }
        }
    }
}

@ThemedPreview
@Composable
private fun SuggestionsRowPreview() {
    TextAssistantApplicationTheme {
        SuggestionsRow(
            suggestions = listOf("hello", "slow first", "error"),
            onSuggestionClick = {},
        )
    }
}
