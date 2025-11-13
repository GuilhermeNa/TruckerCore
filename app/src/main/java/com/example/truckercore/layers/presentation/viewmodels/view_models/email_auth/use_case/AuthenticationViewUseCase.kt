package com.example.truckercore.layers.presentation.viewmodels.view_models.email_auth.use_case

import com.example.truckercore.core.classes.EmailCredential
import com.example.truckercore.core.expressions.mapAppResult
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain._shared.helpers.ViewError
import com.example.truckercore.domain._shared.helpers.ViewResult

class AuthenticationViewUseCase(private val authManager: AuthManager) {

    suspend operator fun invoke(credential: EmailCredential): ViewResult<Unit> {
        return authManager.createUserWithEmail(credential).mapAppResult(
            onSuccess = { ViewResult.Success(Unit) },
            onError = { handleError(it) }
        )
    }

    private fun handleError(exception: AppException): ViewResult.Error {
        if (exception is NetworkUnavailable) {
            val error = ViewError.Recoverable(CONNECTION_ERROR)
            return ViewResult.Error(error)
        }

        if (exception is InfraException.AuthError && AuthErrorCode.CreateUserWithEmail) {
            val error = when (exception.code) {
              AuthErrorCode.CreateUserWithEmail.InvalidCredential ->
                  ViewError.Recoverable(INVALID_CREDENTIAL)

              AuthErrorCode.CreateUserWithEmail.TaskFailure ->
                  ViewError.Critical

              AuthErrorCode.CreateUserWithEmail.Unknown ->
                  ViewError.Critical

              AuthErrorCode.CreateUserWithEmail.UserCollision ->
                  ViewError.Recoverable(COLLISION_USER)

                AuthErrorCode.CreateUserWithEmail.WeakPassword ->
                    ViewError.Recoverable(WEEK_PASSWORD)
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