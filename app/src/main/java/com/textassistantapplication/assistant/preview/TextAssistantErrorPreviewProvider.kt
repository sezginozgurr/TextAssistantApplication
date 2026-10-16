package com.textassistantapplication.assistant.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.textassistantapplication.assistant.TextAssistantError

internal class TextAssistantErrorPreviewProvider : PreviewParameterProvider<TextAssistantError> {
    override val values = sequenceOf(
        TextAssistantError.ServiceUnavailable,
        TextAssistantError.Unknown,
    )
}
