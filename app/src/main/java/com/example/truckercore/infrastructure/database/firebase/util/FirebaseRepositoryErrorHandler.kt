package com.example.truckercore.infrastructure.database.firebase.util

import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Helper object to handle and build error responses for Firebase interactions.
 * This object processes different types of Firebase-related exceptions and creates
 * structured error messages to be used in the application.
 */
internal object FirebaseRepositoryErrorHandler {

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
    fun buildErrorResponse(throwable: Throwable): Response.Error {
        val message = when (throwable) {
            is FirebaseConversionException -> "Error during conversion of Firestore document to DTO class."
            is FirebaseNetworkException -> "Network error. Please check your internet connection."
            is FirebaseFirestoreException -> "Firestore exception occurred. It could be due to invalid data, permissions, or query failures."
            is FirebaseAuthException -> "Authentication failed. Ensure user is authenticated properly."
            else -> "Unknown error."
        }
        logError("An error occurred while interacting with firebase. Error: $throwable")
        return Response.Error(throwable as Exception)
    }

}