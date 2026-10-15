package com.textassistantapplication.core.mvi

import kotlinx.coroutines.flow.StateFlow

interface MVI<UiState, UiAction> {
    val uiState: StateFlow<UiState>

    val currentUiState: UiState

    fun onAction(uiAction: UiAction)

    fun updateUiState(block: UiState.() -> UiState)
}

fun <UiState, UiAction> mvi(
    initialState: UiState,
): MVI<UiState, UiAction> = MVIDelegate(initialState)
