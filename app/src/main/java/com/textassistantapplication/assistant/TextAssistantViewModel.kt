package com.textassistantapplication.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.textassistantapplication.core.mvi.MVI
import com.textassistantapplication.core.mvi.mvi
import com.textassistantapplication.data.TextService
import com.textassistantapplication.assistant.TextAssistantContract.Status
import com.textassistantapplication.assistant.TextAssistantContract.UiAction
import com.textassistantapplication.assistant.TextAssistantContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
class TextAssistantViewModel @Inject constructor(
    private val textService: TextService,
) : ViewModel(),
    MVI<UiState, UiAction> by mvi(UiState()) {

    private var activeRequest: Job? = null

    private var latestRequestId = 0L

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnInputChange -> updateUiState { copy(input = uiAction.input) }
            is UiAction.OnSuggestionClick -> updateUiState { copy(input = uiAction.suggestion) }
            UiAction.OnGenerateClick -> handleGenerate()
            UiAction.OnCancelClick -> handleCancel()
        }
    }

    private fun handleGenerate() {
        val input = currentUiState.input
        if (input.isBlank()) return

        activeRequest?.cancel()
        val requestId = ++latestRequestId
        updateUiState { copy(status = Status.Loading) }

        activeRequest = viewModelScope.launch {
            val status = try {
                Status.Success(textService.generate(input))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Status.Error(e.message)
            }
            if (requestId == latestRequestId) {
                updateUiState { copy(status = status) }
            }
        }
    }

    private fun handleCancel() {
        if (!currentUiState.isLoading) return

        activeRequest?.cancel()
        activeRequest = null
        latestRequestId++
        updateUiState { copy(status = Status.Idle) }
    }
}
