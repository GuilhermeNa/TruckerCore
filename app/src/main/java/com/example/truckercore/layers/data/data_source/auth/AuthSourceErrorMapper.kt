package com.example.truckercore.layers.data.data_source.auth

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.error.core.ErrorMapper
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthSourceErrorMapper : ErrorMapper {

    override fun invoke(e: Throwable): AppException = when (e) {
        is FirebaseNetworkException -> InfraException.Network(cause = e)
        is FirebaseAuthInvalidUserException -> DataException.InvalidUser(cause = e)
        is FirebaseAuthWeakPasswordException -> DataException.WeakPassword(cause = e)
        is FirebaseAuthUserCollisionException -> DataException.UserCollision(cause = e)
        is FirebaseTooManyRequestsException -> DataException.TooManyRequests(cause = e)
        is FirebaseAuthInvalidCredentialsException -> DataException.InvalidCredentials(cause = e)
        else -> DataException.Unknown(cause = e)
    }

}