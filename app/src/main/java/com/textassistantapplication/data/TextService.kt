package com.textassistantapplication.data

interface TextService {
    suspend fun generate(text: String): String
}

class ServiceUnavailableException : Exception(MESSAGE) {
    private companion object {
        const val MESSAGE = "The service is currently unavailable. Please try again."
    }
}
