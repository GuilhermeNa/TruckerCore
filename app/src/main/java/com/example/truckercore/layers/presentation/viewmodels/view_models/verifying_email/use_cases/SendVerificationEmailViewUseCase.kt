package com.example.truckercore.layers.presentation.viewmodels.view_models.verifying_email.use_cases

import com.example.truckercore.core.expressions.mapAppResult
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain._shared.helpers.ViewError
import com.example.truckercore.domain._shared.helpers.ViewResult

class SendVerificationEmailViewUseCase(private val authManager: AuthManager) {

    suspend operator fun invoke(): ViewResult<Unit> {
        return authManager.sendVerificationEmail().mapAppResult(
            onSuccess = { ViewResult.Success(Unit) },
            onError = { handleSendEmailError(it) }
        )
    }

    private fun handleSendEmailError(e: Exception): ViewResult<Unit> {
        val viewError =
            if (e is com.example.truckercore.core.error.classes.data.InfraException.NetworkUnavailable) ViewError.Recoverable(NETWORK_ERROR)
            else ViewError.Critical

        return ViewResult.Error(viewError)
    }

    private companion object {
        private const val NETWORK_ERROR =
            "Falha na conexão. Verifique a internet e tente novamente."
    }


}