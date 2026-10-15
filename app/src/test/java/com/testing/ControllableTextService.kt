package com.appnatextassistant.testing

import com.textassistantapplication.data.ServiceUnavailableException
import com.textassistantapplication.data.TextService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

/**
 * Lets a test decide when each request finishes. Like the real service it ignores cancellation:
 * a pending call is only finished by [complete] or [fail], never by canceling the caller.
 */
class ControllableTextService : TextService {

    private val pending = mutableMapOf<String, CompletableDeferred<String>>()

    override suspend fun generate(text: String): String {
        val response = CompletableDeferred<String>().also { pending[text] = it }
        return withContext(NonCancellable) { response.await() }
    }

    fun complete(text: String, result: String = "Result: $text") {
        pendingFor(text).complete(result)
    }

    fun fail(text: String, error: Throwable = ServiceUnavailableException()) {
        pendingFor(text).completeExceptionally(error)
    }

    private fun pendingFor(text: String): CompletableDeferred<String> =
        pending.remove(text) ?: error("No pending request for \"$text\"")
}
