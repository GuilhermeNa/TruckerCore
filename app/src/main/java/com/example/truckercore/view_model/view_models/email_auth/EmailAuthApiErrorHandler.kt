package com.example.truckercore.view_model.view_models.email_auth

import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.view._shared.ui_error.UiError

object EmailAuthApiErrorHandler {

    operator fun invoke(e: Exception): UiError {
        if (e is InfraException.NetworkUnavailable) return UiError.Recoverable("Falha na conexão")

        if (e !is InfraException.AuthError) return UiError.Critical()

        return when (e.code) {
            AuthErrorCode.CreateUserWithEmail.WeakPassword -> UiError.Recoverable("A senha deve ter entre 6 e 12 carecteres")
            AuthErrorCode.CreateUserWithEmail.InvalidCredential -> UiError.Recoverable("Credenciais inválidas para criação de usuário")
            AuthErrorCode.CreateUserWithEmail.UserCollision -> UiError.Recoverable("Email já cadastrado")
            else -> UiError.Critical()
        }
    }

}