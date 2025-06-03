package com.example.truckercore.view_model.view_models.login

import com.example.truckercore._shared.expressions.mapAppResult
import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.errors.infra.error_code.AuthErrorCode
import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view._shared.ui_error.UiError
import com.example.truckercore.view._shared.ui_error.UiResult

class LoginViewUseCase(private val authService: AuthManager) {

    suspend operator fun invoke(credentials: EmailCredential): UiResult {
            return authService.signIn(credentials).mapAppResult(
                onSuccess = { UiResult.Success },
                onError = { handleError(it) }
            )
    }

    private fun handleError(e: AppException): UiResult {
        if (e is InfraException.NetworkUnavailable) {
            return UiResult.Error(
                UiError.Recoverable(MSG_NETWORK_ERROR)
            )
        }

        if (e is InfraException.AuthError && e.code is AuthErrorCode.SignInWithEmail) {
            val uiError = when (e.code) {
                AuthErrorCode.SignInWithEmail.InvalidCredential -> UiError.Recoverable(MSG_INVALID_CREDENTIAL)
                AuthErrorCode.SignInWithEmail.InvalidUser -> UiError.Recoverable(MSG_INVALID_USER)
                AuthErrorCode.SignInWithEmail.TaskFailure -> UiError.Critical(MSG_TASK_FAILURE)
                AuthErrorCode.SignInWithEmail.TooManyRequests -> UiError.Recoverable(MSG_TOO_MANY_REQUESTS)
                AuthErrorCode.SignInWithEmail.Unknown -> UiError.Critical(MSG_UNKNOWN_AUTH_ERROR)
            }

            return UiResult.Error(uiError)
        }

        return UiResult.Error(
            UiError.Critical(MSG_GENERIC_UNKNOWN_ERROR)
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

