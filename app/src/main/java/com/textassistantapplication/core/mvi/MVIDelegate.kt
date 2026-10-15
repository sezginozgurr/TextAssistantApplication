package com.textassistantapplication.core.mvi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MVIDelegate<UiState, UiAction>(initialUiState: UiState) : MVI<UiState, UiAction> {

    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    override val currentUiState: UiState
        get() = uiState.value

    override fun onAction(uiAction: UiAction) = Unit

    override fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }
}
