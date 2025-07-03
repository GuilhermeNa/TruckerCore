package com.example.truckercore.view_model.view_models.verifying_email.use_cases

import com.example.truckercore._shared.expressions.mapAppResult
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared.helpers.ViewError
import com.example.truckercore.view_model._shared.helpers.ViewResult

class SendVerificationEmailViewUseCase(private val authManager: AuthManager) {

    suspend operator fun invoke(): ViewResult<Unit> {
        return authManager.sendVerificationEmail().mapAppResult(
            onSuccess = { ViewResult.Success(Unit) },
            onError = { handleSendEmailError(it) }
        )
    }

    private fun handleSendEmailError(e: Exception): ViewResult<Unit> {
        val viewError =
            if (e is InfraException.NetworkUnavailable) ViewError.Recoverable(NETWORK_ERROR)
            else ViewError.Critical

        return ViewResult.Error(viewError)
    }

    private companion object {
        private const val NETWORK_ERROR =
            "Falha na conexão. Verifique a internet e tente novamente."
    }


}