package com.example.truckercore.infrastructure.database.firebase

import android.util.Log
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.configs.app_constants.Tag
import com.example.truckercore.infrastructure.database.firebase.exceptions.FirebaseConversionException
import com.example.truckercore.infrastructure.database.firebase.exceptions.NullDocumentSnapShotException
import com.example.truckercore.infrastructure.database.firebase.exceptions.NullQuerySnapShotException
import com.example.truckercore.shared.utils.Response
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

internal abstract class Repository<T>(
    open val firebaseRepository: FirebaseRepository,
    open val queryBuilder: FirebaseQueryBuilder,
    open val converter: FirebaseConverter<T>,
    open val collectionName: String
) {

    /**
     * Handles errors that occur during Firebase operations.
     *
     * This method logs the error, including the repository name and the ID message,
     * for debugging purposes. It also returns a generic [Response.Error] with the
     * exception to indicate the failure.
     *
     * @param e The exception that occurred during the operation.
     * @return A [Response.Error] containing the exception.
     */
    protected fun handleErrors(e: Exception): Response.Error {
        when (e) {
            is FirebaseConversionException -> Log.e(Tag.ERROR, buildErrorMessage(e))

            is FirebaseNetworkException -> Log.e(Tag.ERROR, buildErrorMessage(e))

            is FirebaseAuthInvalidCredentialsException -> Log.e(Tag.ERROR, buildErrorMessage(e))

            is FirebaseTooManyRequestsException -> Log.e(Tag.ERROR, buildErrorMessage(e))

            is FirebaseException -> Log.e(Tag.ERROR, buildErrorMessage(e))

            is NullDocumentSnapShotException -> Log.e(Tag.ERROR, buildErrorMessage(e))

            is NullQuerySnapShotException -> Log.e(Tag.ERROR, buildErrorMessage(e))

            else -> Log.e(Tag.ERROR, buildErrorMessage(e))
        }
        return Response.Error(e)
    }

    private fun buildErrorMessage(e: Exception): String {
        return "Repository ${this::class.simpleName}: ${e.message}"
    }

}