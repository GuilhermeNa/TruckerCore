package com.example.truckercore.model.infrastructure.data_source.firebase.exceptions

/**
 * Exception thrown when a task is unsuccessful, either due to issues with the provided DTO or an ID.
 *
 * This exception is used when a task related to Firebase (such as a database operation) fails and don't
 * throw any exception.
 */
class IncompleteTaskException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
