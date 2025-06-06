package com.example.truckercore.view_model.view_models.email_auth

import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.view_model._shared.helpers.ViewError

object EmailAuthApiErrorHandler {

    operator fun invoke(e: Exception): ViewError {
        if (e is InfraException.NetworkUnavailable) return ViewError.Recoverable("Falha na conexão")

        if (e !is InfraException.AuthError) return ViewError.Critical

        return when (e.code) {
            AuthErrorCode.CreateUserWithEmail.WeakPassword -> ViewError.Recoverable("A senha deve ter entre 6 e 12 carecteres")
            AuthErrorCode.CreateUserWithEmail.InvalidCredential -> ViewError.Recoverable("Credenciais inválidas para criação de usuário")
            AuthErrorCode.CreateUserWithEmail.UserCollision -> ViewError.Recoverable("Email já cadastrado")
            else -> ViewError.Critical
        }
    }

}