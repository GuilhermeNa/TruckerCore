package com.example.truckercore.model.infrastructure.data_source.firebase.auth

import com.example.truckercore.model.infrastructure.integration.auth.for_api.AuthSourceErrorMapper
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.AuthSourceException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.InvalidCredentialsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.SessionInactiveException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TaskFailureException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.TooManyRequestsException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.UnknownException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.UserCollisionException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.WeakPasswordException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FirebaseAuthErrorMapper : AuthSourceErrorMapper {

    override fun creatingUserWithEmail(e: Throwable): AuthSourceException {
        return when (e) {
            is TaskFailureException -> TaskFailureException(
                message = "Failed to create user with email. Please try again later."
            )

            is FirebaseAuthWeakPasswordException -> WeakPasswordException(
                message = "The password provided is too weak. Please choose a stronger password.",
                cause = e
            )

            is FirebaseAuthInvalidCredentialsException -> InvalidCredentialsException(
                "The email address or password is invalid. Please check your credentials.",
                cause = e
            )

            is FirebaseAuthUserCollisionException -> UserCollisionException(
                message = "An account already exists with this email address.",
                cause = e
            )

            is FirebaseNetworkException -> NetworkException(
                message = "A network error occurred while creating user with email.",
                cause = e
            )

            else -> UnknownException(
                message = "An unknown error occurred during user creation with email.",
                cause = e
            )
        }
    }

    override fun sendingEmailVerification(e: Throwable): AuthSourceException {
        return when (e) {
            is SessionInactiveException -> SessionInactiveException(
                message = "The user profile is invalid or incomplete for sending email verification."
            )

            is TaskFailureException -> TaskFailureException(
                message = "Failed to send email verification. Please try again."
            )

            else -> UnknownException(
                message = "An unknown error occurred while sending email verification.",
                cause = e
            )
        }
    }

    override fun updatingProfile(e: Throwable): AuthSourceException {
        return when (e) {
            is FirebaseNetworkException -> NetworkException(
                message = "A network error occurred while updating the user profile.",
                cause = e
            )

            is SessionInactiveException -> SessionInactiveException(
                message = "The data provided for updating the profile is invalid."
            )

            is TaskFailureException -> TaskFailureException(
                message = "Failed to update user profile. Please try again."
            )

            else -> UnknownException(
                message = "An unknown error occurred while updating the user profile.",
                cause = e
            )
        }
    }

    override fun signingInWithEmail(e: Throwable): AuthSourceException {
        return when (e) {
            is FirebaseAuthInvalidUserException -> InvalidCredentialsException(
                message = "The user account was not found or may have been deleted. " +
                        "Please verify your credentials.",
                cause = e
            )

            is FirebaseAuthInvalidCredentialsException -> InvalidCredentialsException(
                message = "Invalid email or password. Please try again.",
                cause = e
            )

            is FirebaseNetworkException -> NetworkException(
                message = "A network error occurred while attempting to sign in with email.",
                cause = e
            )

            is FirebaseTooManyRequestsException -> TooManyRequestsException(
                message = "Too many login attempts. Please wait and try again later.",
                cause = e
            )

            is TaskFailureException -> TaskFailureException(
                message = "Sign-in with email failed. Please try again."
            )

            else -> UnknownException(
                message = "An unknown error occurred while signing in with email.",
                cause = e
            )
        }
    }

    override fun sendPasswordResetEmail(e: Throwable): AuthSourceException {
        return when (e) {
            is TaskFailureException -> TaskFailureException(
                message = "Failed to send reset email. Please try again."
            )

            else -> UnknownException(
                message = "An unknown error occurred while sending reset email.",
                cause = e
            )
        }
    }


}