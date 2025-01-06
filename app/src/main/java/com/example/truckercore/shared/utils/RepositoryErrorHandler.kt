package com.example.truckercore.shared.utils

import android.util.Log
import com.example.truckercore.configs.app_constants.Tag
import com.example.truckercore.infrastructure.database.firebase.exceptions.FirebaseConversionException
import com.example.truckercore.infrastructure.database.firebase.exceptions.NullDocumentSnapShotException
import com.example.truckercore.infrastructure.database.firebase.exceptions.NullQuerySnapShotException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

object RepositoryErrorHandler {

    fun handleException(e: Exception): Response.Error {

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