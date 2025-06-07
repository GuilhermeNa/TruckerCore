package com.example.truckercore.view_model.view_models.email_auth.use_case

import com.example.truckercore._shared.expressions.mapAppResult
import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared.helpers.ViewError
import com.example.truckercore.view_model._shared.helpers.ViewResult

class AuthenticationViewUseCase(private val authManager: AuthManager) {

    suspend operator fun invoke(credential: EmailCredential): ViewResult {
        return authManager.createUserWithEmail(credential).mapAppResult(
            onSuccess = { ViewResult.Success },
            onError = { handleError(it) }
        )
    }

    private fun handleError(exception: AppException): ViewResult.Error {
        if (exception is InfraException.NetworkUnavailable) {
            val error = ViewError.Recoverable(CONNECTION_ERROR)
            return ViewResult.Error(error)
        }

        if (exception is InfraException.AuthError &&
            exception.code is AuthErrorCode.CreateUserWithEmail
        ) {
            val error = when (exception.code) {
                AuthErrorCode.CreateUserWithEmail.InvalidCredential -> ViewError.Recoverable(
                    INVALID_CREDENTIAL
                )

                AuthErrorCode.CreateUserWithEmail.TaskFailure -> ViewError.Critical
                AuthErrorCode.CreateUserWithEmail.Unknown -> ViewError.Critical
                AuthErrorCode.CreateUserWithEmail.UserCollision -> ViewError.Recoverable(
                    COLLISION_USER
                )

                AuthErrorCode.CreateUserWithEmail.WeakPassword -> ViewError.Recoverable(
                    WEEK_PASSWORD
                )
            }
            return ViewResult.Error(error)
        }

        return ViewResult.Error(ViewError.Critical)
    }

    companion object {
        const val CONNECTION_ERROR =
            "Sem conexão com a internet. Verifique sua rede e tente novamente."
        const val INVALID_CREDENTIAL =
            "E-mail ou senha inválidos. Verifique os dados e tente novamente."
        const val COLLISION_USER =
            "Este e-mail já está cadastrado. Tente recuperar a senha ou usar outro e-mail."
        const val WEEK_PASSWORD = "A senha é muito fraca. Use entre 6 e 12 números."
    }

}