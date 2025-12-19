package com.example.truckercore.layers.data.data_source.auth

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.error.core.ErrorMapper
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

/**
 * Maps Firebase authentication exceptions to domain-specific [AppException]s.
 *
 * This class implements [ErrorMapper] and is used by [AuthSourceImpl] to convert
 * FirebaseAuth-specific exceptions into application-level exceptions that are
 * meaningful for the domain layer.
 */
class AuthSourceErrorMapper : ErrorMapper {

    override fun invoke(e: Throwable): AppException = when (e) {
        is FirebaseNetworkException -> InfraException.Network(cause = e)
        is FirebaseAuthInvalidUserException -> DomainException.InvalidUser(cause = e)
        is FirebaseAuthWeakPasswordException -> DomainException.WeakPassword(cause = e)
        is FirebaseAuthUserCollisionException -> DomainException.UserCollision(cause = e)
        is FirebaseTooManyRequestsException -> DataException.TooManyRequests(cause = e)
        is FirebaseAuthInvalidCredentialsException -> DomainException.InvalidCredentials(cause = e)
        is FirebaseAuthException -> DataException.AuthSource(cause = e)
        else -> DataException.Unknown(cause = e)
    }

}