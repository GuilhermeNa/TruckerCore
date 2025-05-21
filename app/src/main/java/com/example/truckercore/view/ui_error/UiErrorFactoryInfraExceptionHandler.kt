package com.example.truckercore.view.ui_error

import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.model.errors.infra.InfraException

object UiErrorFactoryInfraExceptionHandler {

   /* operator fun invoke(e: InfraException): UiError = when (e) {
        is InfraException.NetworkUnavailable -> UiError.Recoverable.Network
        is InfraException.AuthError -> handleAuthError(e.code)
        is InfraException.DatabaseError -> TODO()
        is InfraException.WriterError -> TODO()
    }

    private fun handleAuthError(code: AuthErrorCode): UiError = when (code) {
        AuthErrorCode.CreateUserWithEmail.InvalidCredential -> UiError.Recoverable.Custom("E-mail ou senha inválidos.")
        AuthErrorCode.CreateUserWithEmail.UserCollision -> UiError.Recoverable.Custom("E-mail já cadastrado.")
        AuthErrorCode.CreateUserWithEmail.WeakPassword -> UiError.Recoverable.Custom("A senha é muito fraca.")
        AuthErrorCode.CreateUserWithEmail.TaskFailure -> UiError.Critical()
        AuthErrorCode.CreateUserWithEmail.Unknown -> UiError.Critical()

        AuthErrorCode.EmailVerification.TaskFailure -> UiError.Critical()
        AuthErrorCode.EmailVerification.Unknown -> UiError.Critical()

        AuthErrorCode.ObservingEmailValidation -> UiError.Critical()

        AuthErrorCode.RecoverEmail.InvalidEmail -> UiError.Recoverable.Custom("Formato de e-mail inválido. Tente novamente.")
        AuthErrorCode.RecoverEmail.TaskFailure -> UiError.Critical()
        AuthErrorCode.RecoverEmail.Unknown -> UiError.Critical()

        AuthErrorCode.SignInWithEmail.InvalidCredential -> UiError.Recoverable.Custom("Credenciais inválidas.")
        AuthErrorCode.SignInWithEmail.InvalidUser -> UiError.Recoverable.Custom("Usuário inválido.")
        AuthErrorCode.SignInWithEmail.TaskFailure -> UiError.Critical()
        AuthErrorCode.SignInWithEmail.TooManyRequests -> UiError.Recoverable.Custom("Limite de tentativas excedido. Aguarde um momento e tente novamente.")
        AuthErrorCode.SignInWithEmail.Unknown -> UiError.Critical()

        AuthErrorCode.SessionInactive -> UiError.Recoverable.SessionInactive*/
    //}

    /* private fun handleDatabaseError(code: DatabaseErrorCode): UiError {
         TODO()
     }

     private fun handleWriterError(code: WriterErrorCode): UiError {
         TODO()
     }*/

}