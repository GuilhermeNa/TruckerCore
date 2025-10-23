package com.example.truckercore.layers.presentation.viewmodels.view_models.forget_password

import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.expressions.mapAppResult
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain._shared.helpers.ViewError
import com.example.truckercore.domain._shared.helpers.ViewResult

class ResetPasswordViewUseCase(private val authManager: AuthManager) {

    suspend operator fun invoke(email: Email): ViewResult<Unit> =
        authManager.resetPassword(email).mapAppResult(
            onSuccess = { ViewResult.Success(Unit) },
            onError = { handleError(it) }
        )

    private fun handleError(e: com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException): ViewResult.Error {
        if (e is com.example.truckercore.core.error.classes.data.InfraException.NetworkUnavailable) {
            return ViewResult.Error(ViewError.Recoverable(com.example.truckercore.presentation.viewmodels.view_models.forget_password.ResetPasswordViewUseCase.Companion.MSG_NETWORK_ERROR))
        }

        if (e is com.example.truckercore.core.error.classes.data.InfraException.AuthError && e.code is _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.RecoverEmail) {
            val uiError = when (e.code) {
                _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.RecoverEmail.InvalidEmail -> ViewError.Recoverable(
                    com.example.truckercore.presentation.viewmodels.view_models.forget_password.ResetPasswordViewUseCase.Companion.MSG_INVALID_CREDENTIAL
                )

                _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.RecoverEmail.TaskFailure -> ViewError.Critical
                _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.RecoverEmail.Unknown -> ViewError.Critical
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