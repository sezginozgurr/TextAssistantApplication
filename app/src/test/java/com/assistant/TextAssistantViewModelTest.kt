package com.appnatextassistant.ui.assistant

import com.appnatextassistant.testing.ControllableTextService
import com.appnatextassistant.testing.MainDispatcherRule
import com.textassistantapplication.assistant.TextAssistantContract.Status
import com.textassistantapplication.assistant.TextAssistantContract.UiAction
import com.textassistantapplication.assistant.TextAssistantContract.UiState
import com.textassistantapplication.assistant.TextAssistantViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TextAssistantViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val service = ControllableTextService()
    private val viewModel = TextAssistantViewModel(service)

    @Test
    fun `older request completing after a newer one keeps the current result`() =
        runTest(mainDispatcherRule.dispatcher) {
            viewModel.submit("slow first")
            runCurrent()
            viewModel.submit("second")
            runCurrent()

            service.complete("second")
            runCurrent()
            service.complete("slow first")
            runCurrent()

            assertEquals(
                UiState(input = "second", status = Status.Success("Result: second")),
                viewModel.currentUiState,
            )
        }

    @Test
    fun `late result after cancellation is not displayed`() =
        runTest(mainDispatcherRule.dispatcher) {
            viewModel.submit("slow first")
            runCurrent()
            viewModel.onAction(UiAction.OnCancelClick)

            service.complete("slow first")
            runCurrent()

            assertEquals(
                UiState(input = "slow first", status = Status.Idle),
                viewModel.currentUiState,
            )
        }

    private fun TextAssistantViewModel.submit(text: String) {
        onAction(UiAction.OnInputChange(text))
        onAction(UiAction.OnGenerateClick)
    }
}
