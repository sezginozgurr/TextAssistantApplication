package com.textassistantapplication.assistant.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.textassistantapplication.assistant.TextAssistantContract.Status
import com.textassistantapplication.assistant.TextAssistantContract.UiState
import com.textassistantapplication.assistant.TextAssistantError

/** One sample per screen state. Add new cases here as the screen grows. */
internal class UiStatePreviewProvider : PreviewParameterProvider<UiState> {
    override val values = sequenceOf(
        UiState(),
        UiState(input = "slow first", status = Status.Loading),
        UiState(input = "hello", status = Status.Success("Result: hello")),
        UiState(input = "error", status = Status.Error(TextAssistantError.ServiceUnavailable)),
        UiState(input = "error", status = Status.Error(TextAssistantError.Unknown)),
    )
}
