package com.textassistantapplication.assistant

import com.textassistantapplication.data.ServiceUnavailableException

/**
 * Errors the screen knows how to show. Raw exception messages never reach the UI; each case is
 * mapped to a string resource where it is rendered.
 */
sealed interface TextAssistantError {
    data object ServiceUnavailable : TextAssistantError
    data object Unknown : TextAssistantError
}

internal fun Throwable.toTextAssistantError(): TextAssistantError = when (this) {
    is ServiceUnavailableException -> TextAssistantError.ServiceUnavailable
    else -> TextAssistantError.Unknown
}
