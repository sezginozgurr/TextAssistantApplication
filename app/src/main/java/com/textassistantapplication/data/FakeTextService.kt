package com.textassistantapplication.data

import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Simulates a third-party SDK without cancellation support. [NonCancellable] lets the delay finish
 * after the caller is canceled, and because no dispatcher is switched, `withContext` returns the
 * normal result or error instead of throwing a cancellation exception.
 */
class FakeTextService @Inject constructor() : TextService {

    override suspend fun generate(text: String): String = withContext(NonCancellable) {
        val input = text.trim()
        delay(if (input.startsWith(SLOW_PREFIX, ignoreCase = true)) SLOW_DELAY else NORMAL_DELAY)
        respond(input)
    }

    private fun respond(input: String): String {
        val isError = ERROR_PREFIXES.any { input.startsWith(it, ignoreCase = true) }
        if (isError) throw ServiceUnavailableException()
        return "Result: $input"
    }

    private companion object {
        const val SLOW_PREFIX = "slow"
        val ERROR_PREFIXES = listOf("error", "slow error")
        val NORMAL_DELAY = 300.milliseconds
        val SLOW_DELAY = 2.seconds
    }
}
