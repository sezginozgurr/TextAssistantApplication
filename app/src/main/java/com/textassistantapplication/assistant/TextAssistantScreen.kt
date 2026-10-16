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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.textassistantapplication.assistant.TextAssistantContract.Status
import com.textassistantapplication.assistant.TextAssistantContract.UiAction
import com.textassistantapplication.assistant.TextAssistantContract.UiState
import com.textassistantapplication.assistant.components.ActionButtons
import com.textassistantapplication.assistant.components.ErrorCard
import com.textassistantapplication.assistant.components.Heading
import com.textassistantapplication.assistant.components.InputField
import com.textassistantapplication.assistant.components.LoadingCard
import com.textassistantapplication.assistant.components.SuccessCard
import com.textassistantapplication.assistant.components.SuggestionsRow
import com.textassistantapplication.assistant.preview.UiStatePreviewProvider
import com.textassistantapplication.core.share.shareText
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme
import com.textassistantapplication.uitoolkit.Spacing
import com.textassistantapplication.uitoolkit.ThemedPreview

private val Suggestions = listOf("hello", "slow first", "error")

@Composable
fun TextAssistantRoute(viewModel: TextAssistantViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    TextAssistantScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        onShareClick = { viewModel.currentUiState.shareableResult?.let(context::shareText) },
    )
}

@Composable
fun TextAssistantScreen(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    onShareClick: () -> Unit,
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
    onShareClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.XLarge, vertical = Spacing.Large),
        verticalArrangement = Arrangement.spacedBy(Spacing.Large),
    ) {
        Heading()
        InputField(
            value = uiState.input,
            onValueChange = { onAction(UiAction.OnInputChange(it)) },
        )
        ActionButtons(
            isLoading = uiState.isLoading,
            generateIcon = uiState.status.generateIcon(),
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
                    onShareClick = onShareClick,
                )

                is Status.Error -> ErrorCard(error = status.error)
            }
        }
    }
}

/** After an error, Generate retries the same text, so it shows a retry icon. */
private fun Status.generateIcon(): ImageVector = when (this) {
    is Status.Error -> Icons.Filled.Refresh
    else -> Icons.AutoMirrored.Filled.Send
}

@ThemedPreview
@Composable
private fun TextAssistantScreenPreview(
    @PreviewParameter(UiStatePreviewProvider::class) uiState: UiState,
) {
    TextAssistantApplicationTheme {
        TextAssistantScreen(uiState = uiState, onAction = {}, onShareClick = {})
    }
}
