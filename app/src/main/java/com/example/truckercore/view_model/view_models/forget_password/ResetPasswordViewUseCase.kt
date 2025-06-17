package com.example.truckercore.view_model.view_models.forget_password

import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.expressions.mapAppResult
import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared.helpers.ViewError
import com.example.truckercore.view_model._shared.helpers.ViewResult

class ResetPasswordViewUseCase(private val authManager: AuthManager) {

    suspend operator fun invoke(email: Email): ViewResult<Unit> =
        authManager.resetPassword(email).mapAppResult(
            onSuccess = { ViewResult.Success(Unit) },
            onError = { handleError(it) }
        )

    private fun handleError(e: AppException): ViewResult.Error {
        if (e is InfraException.NetworkUnavailable) {
            return ViewResult.Error(ViewError.Recoverable(MSG_NETWORK_ERROR))
        }

        if (e is InfraException.AuthError && e.code is AuthErrorCode.RecoverEmail) {
            val uiError = when (e.code) {
                AuthErrorCode.RecoverEmail.InvalidEmail -> ViewError.Recoverable(
                    MSG_INVALID_CREDENTIAL
                )

                AuthErrorCode.RecoverEmail.TaskFailure -> ViewError.Critical
                AuthErrorCode.RecoverEmail.Unknown -> ViewError.Critical
            }
            return ViewResult.Error(uiError)
        }

        return ViewResult.Error(ViewError.Critical)
    }

    companion object {
        private const val MSG_NETWORK_ERROR =
            "Falha na conexão. Verifique sua conexão com a internet e tente novamente."

        private const val MSG_INVALID_CREDENTIAL = "E-mail inválido"
    }

}