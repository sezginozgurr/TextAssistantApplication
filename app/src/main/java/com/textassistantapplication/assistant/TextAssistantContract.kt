package com.textassistantapplication.assistant

object TextAssistantContract {

    data class UiState(
        val input: String = "",
        val status: Status = Status.Idle,
    ) {
        val isLoading: Boolean get() = status is Status.Loading
    }

    sealed interface Status {
        data object Idle : Status
        data object Loading : Status
        data class Success(val result: String) : Status
        data class Error(val message: String?) : Status
    }

    sealed interface UiAction {
        data class OnInputChange(val input: String) : UiAction
        data class OnSuggestionClick(val suggestion: String) : UiAction
        data object OnGenerateClick : UiAction
        data object OnCancelClick : UiAction
    }
}
