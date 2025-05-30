package com.example.truckercore.view_model.view_models.forget_password

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.expressions.mapAppResult
import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view.ui_error.UiError
import com.example.truckercore.view.ui_error.UiResult

class ResetPasswordViewUseCase(private val authManager: AuthManager) {

    suspend operator fun invoke(email: Email): UiResult =
        authManager.resetPassword(email).mapAppResult(
            onSuccess = { UiResult.Success },
            onError = { handleError(it) }
        )

    private fun handleError(e: AppException): UiResult.Error {
        if (e is InfraException.NetworkUnavailable) {
            return UiResult.Error(UiError.Recoverable(MSG_NETWORK_ERROR))
        }

        if (e is InfraException.AuthError && e.code is AuthErrorCode.RecoverEmail) {
            val uiError = when (e.code) {
                AuthErrorCode.RecoverEmail.InvalidEmail -> UiError.Recoverable(MSG_INVALID_CREDENTIAL)
                AuthErrorCode.RecoverEmail.TaskFailure -> UiError.Critical()
                AuthErrorCode.RecoverEmail.Unknown -> UiError.Critical()
            }
            return UiResult.Error(uiError)
        }

        return UiResult.Error(UiError.Critical())
    }

    companion object {
        private const val MSG_NETWORK_ERROR =
            "Falha na conexão. Verifique sua conexão com a internet e tente novamente."

        private const val MSG_INVALID_CREDENTIAL = "E-mail inválido"
    }

}