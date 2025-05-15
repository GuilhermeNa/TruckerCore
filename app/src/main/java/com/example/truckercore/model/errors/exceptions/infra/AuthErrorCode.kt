package com.example.truckercore.model.errors.exceptions.infra

sealed class AuthErrorCode : InfraErrorCode {

    data object SessionInactive: AuthErrorCode()

    sealed class CreateUserWithEmail : AuthErrorCode() {
        data object TaskFailure : CreateUserWithEmail()
        data object WeakPassword : CreateUserWithEmail()
        data object InvalidCredential : CreateUserWithEmail()
        data object UserCollision : CreateUserWithEmail()
        data object Unknown : CreateUserWithEmail()
    }

    sealed class EmailVerification : AuthErrorCode() {
        data object TaskFailure : EmailVerification()
        data object Unknown : EmailVerification()
    }

    sealed class SignInWithEmail : AuthErrorCode() {
        data object InvalidUser : SignInWithEmail()
        data object InvalidCredential : SignInWithEmail()
        data object TooManyRequests : SignInWithEmail()
        data object TaskFailure : SignInWithEmail()
        data object Unknown : SignInWithEmail()
    }

    data object ObservingEmailValidation : AuthErrorCode()

    sealed class RecoverEmail : AuthErrorCode() {
        data object InvalidEmail : RecoverEmail()
        data object TaskFailure : RecoverEmail()
        data object Unknown : RecoverEmail()
    }

}