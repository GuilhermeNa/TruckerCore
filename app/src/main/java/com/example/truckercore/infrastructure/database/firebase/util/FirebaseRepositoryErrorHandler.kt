package com.example.truckercore.infrastructure.database.firebase.util

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.shared.utils.Response
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Helper object to handle and build error responses for Firebase interactions.
 * This object processes different types of Firebase-related exceptions and creates
 * structured error messages to be used in the application.
 */
object FirebaseRepositoryErrorHandler {

    /**
     * Builds an error response based on the provided exception.
     *
     * This method identifies the type of exception thrown during Firebase operations
     * (e.g., conversion issues, network failures, Firestore exceptions, or authentication errors)
     * and constructs a detailed error message that will be returned to the caller.
     *
     * @param collection The name of the Firestore collection that was being accessed.
     * @param field The field that was being queried, if applicable. Default is null.
     * @param value The value of the field that caused the issue.
     * @param throwable The exception that was thrown during the Firebase operation.
     * @return A [Response.Error] containing a detailed error message and the thrown exception.
     */
    fun buildErrorResponse(
        collection: Collection,
        field: Field? = null,
        value: String,
        throwable: Throwable
    ): Response.Error {
        val specificMessage = when (throwable) {
            is FirebaseConversionException -> "Error during conversion of Firestore document to DTO class."
            is FirebaseNetworkException -> "A network error occurred while interacting with Firestore. Please check your internet connection."
            is FirebaseFirestoreException -> "Firestore exception occurred. It could be due to invalid data, permissions, or query failures."
            is FirebaseAuthException -> "Authentication failed. Ensure user is authenticated properly."
            else -> "Unknown error occurred while interacting with Firestore."
        }

        val message = baseMessage(specificMessage, collection, field, value)
        return Response.Error(message, throwable as Exception)
    }

    // Constructs a detailed error message based on the provided details.
    private fun baseMessage(
        txt: String,
        collection: Collection,
        field: Field? = null,
        value: String
    ): String = buildString {
        append(" FirebaseRepository:")
        append(" $txt")
        append(" Collection: [$collection],")
        field?.let { append(" Field: [$it],") }
        append(" Value: [$value].")
    }

}