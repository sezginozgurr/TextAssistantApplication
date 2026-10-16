package com.textassistantapplication.assistant.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.textassistantapplication.R
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme
import com.textassistantapplication.uitoolkit.ThemedPreview

private const val InputMinLines = 2
private const val InputMaxLines = 6

@Composable
internal fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.input_placeholder)) },
        minLines = InputMinLines,
        maxLines = InputMaxLines,
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        ),
    )
}

@ThemedPreview
@Composable
private fun InputFieldEmptyPreview() {
    TextAssistantApplicationTheme {
        InputField(value = "", onValueChange = {})
    }
}

@ThemedPreview
@Composable
private fun InputFieldFilledPreview() {
    TextAssistantApplicationTheme {
        InputField(value = "slow first", onValueChange = {})
    }
}
