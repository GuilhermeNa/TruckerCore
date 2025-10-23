package com.example.truckercore.layers.presentation.viewmodels.view_models.login

import com.example.truckercore.core.expressions.mapAppResult
import com.example.truckercore.core.classes.EmailCredential
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain._shared.helpers.ViewError
import com.example.truckercore.domain._shared.helpers.ViewResult

class LoginViewUseCase(private val authService: AuthManager) {

    suspend operator fun invoke(credentials: EmailCredential): ViewResult<Unit> {
            return authService.signIn(credentials).mapAppResult(
                onSuccess = { ViewResult.Success(Unit) },
                onError = { handleError(it) }
            )
    }

    private fun handleError(e: com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException): ViewResult<Unit> {
        if (e is com.example.truckercore.core.error.classes.data.InfraException.NetworkUnavailable) {
            return ViewResult.Error(
                ViewError.Recoverable(com.example.truckercore.layers.presentation.viewmodels.view_models.login.LoginViewUseCase.Companion.MSG_NETWORK_ERROR)
            )
        }

        if (e is com.example.truckercore.core.error.classes.data.InfraException.AuthError && e.code is _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.SignInWithEmail) {
            val uiError = when (e.code) {
                _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.SignInWithEmail.InvalidCredential -> ViewError.Recoverable(
                    com.example.truckercore.layers.presentation.viewmodels.view_models.login.LoginViewUseCase.Companion.MSG_INVALID_CREDENTIAL
                )
                _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.SignInWithEmail.InvalidUser -> ViewError.Recoverable(
                    com.example.truckercore.layers.presentation.viewmodels.view_models.login.LoginViewUseCase.Companion.MSG_INVALID_USER
                )
                _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.SignInWithEmail.TaskFailure -> ViewError.Critical
                _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.SignInWithEmail.TooManyRequests -> ViewError.Recoverable(
                    com.example.truckercore.layers.presentation.viewmodels.view_models.login.LoginViewUseCase.Companion.MSG_TOO_MANY_REQUESTS
                )
                _root_ide_package_.com.example.truckercore.core.error.classes.data.error_code.AuthErrorCode.SignInWithEmail.Unknown -> ViewError.Critical
            }

            return ViewResult.Error(uiError)
        }

        return ViewResult.Error(
            ViewError.Critical
        )
    }

    companion object {
        private const val MSG_NETWORK_ERROR =
            "Falha na conexão. Verifique sua conexão com a internet e tente novamente."

        private const val MSG_INVALID_CREDENTIAL =
            "E-mail ou senha incorretos."

        private const val MSG_INVALID_USER =
            "Usuário não encontrado. Verifique suas credenciais."

        private const val MSG_TASK_FAILURE =
            "Falha ao realizar login. Tente novamente mais tarde."

        private const val MSG_TOO_MANY_REQUESTS =
            "Muitas tentativas seguidas. Aguarde um momento e tente novamente."

        private const val MSG_UNKNOWN_AUTH_ERROR =
            "Ocorreu um erro inesperado ao tentar entrar. Por favor, tente novamente."

        private const val MSG_GENERIC_UNKNOWN_ERROR =
            "Ocorreu um erro desconhecido. Reinicie o app ou entre em contato com o suporte."
    }

}

