package com.example.truckercore.infrastructure.exceptions

import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException

internal class ExceptionHandler {

    fun catch(throwable: Throwable): Response.Error {

        return Response.Error(NullPointerException())
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        throwable as Exception
        val message = when (throwable) {
            is FirebaseConversionException -> "Error during conversion of Firestore document to DTO class."
            is FirebaseNetworkException -> "Network error. Please check your internet connection."
            is FirebaseFirestoreException -> "Firestore exception occurred. It could be due to invalid data, permissions, or query failures."
            is FirebaseAuthException -> "Authentication failed. Ensure user is authenticated properly."
            else -> "Unknown error."
        }
        logError(
            context = javaClass,
            exception = throwable,
            message = message
        )
        return Response.Error(throwable)
    }

}