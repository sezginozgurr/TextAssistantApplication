package com.textassistantapplication.assistant

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appnatextassistant.core.share.shareText
import com.textassistantapplication.assistant.TextAssistantContract.Status
import com.textassistantapplication.assistant.TextAssistantContract.UiAction
import com.textassistantapplication.assistant.TextAssistantContract.UiState
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme

private val Suggestions = listOf("hello", "slow first", "error")

@Composable
fun TextAssistantRoute(viewModel: TextAssistantViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    TextAssistantScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        onShareClick = context::shareText,
    )
}

@Composable
fun TextAssistantScreen(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        TextAssistantContent(uiState = uiState, onAction = onAction, onShareClick = onShareClick)
    }
}

@Composable
private fun TextAssistantContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    onShareClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Heading()
        InputField(
            value = uiState.input,
            onValueChange = { onAction(UiAction.OnInputChange(it)) },
        )
        ActionButtons(
            isLoading = uiState.isLoading,
            isRetry = uiState.status is Status.Error,
            onGenerateClick = { onAction(UiAction.OnGenerateClick) },
            onCancelClick = { onAction(UiAction.OnCancelClick) },
        )
        AnimatedContent(
            targetState = uiState.status,
            modifier = Modifier.fillMaxWidth(),
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            contentKey = { it::class },
            label = "status",
        ) { status ->
            when (status) {
                Status.Idle -> SuggestionsRow(
                    suggestions = Suggestions,
                    onSuggestionClick = { onAction(UiAction.OnSuggestionClick(it)) },
                )

                Status.Loading -> LoadingCard()

                is Status.Success -> SuccessCard(
                    result = status.result,
                    onShareClick = { onShareClick(status.result) },
                )

                is Status.Error -> ErrorCard(message = status.message)
            }
        }
    }
}

private class UiStatePreviewProvider : PreviewParameterProvider<UiState> {
    override val values = sequenceOf(
        UiState(),
        UiState(input = "slow first", status = Status.Loading),
        UiState(input = "hello", status = Status.Success("Result: hello")),
        UiState(
            input = "error",
            status = Status.Error("The service is currently unavailable. Please try again."),
        ),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0A12)
@Composable
private fun TextAssistantScreenPreview(
    @PreviewParameter(UiStatePreviewProvider::class) uiState: UiState,
) {
    TextAssistantApplicationTheme {
        TextAssistantScreen(uiState = uiState, onAction = {}, onShareClick = {})
    }
}
