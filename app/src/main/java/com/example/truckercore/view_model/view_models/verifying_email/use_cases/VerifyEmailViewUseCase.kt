package com.example.truckercore.view_model.view_models.verifying_email.use_cases

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore._shared.expressions.mapAppResult
import com.example.truckercore.model.modules.authentication.manager.AuthManager
import com.example.truckercore.view_model._shared.helpers.ViewResult
import com.example.truckercore.view_model._shared.use_cases.CounterUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.selects.select

class VerifyEmailViewUseCase(
    private val counterUseCase: CounterUseCase,
    private val authManager: AuthManager
) {

    val counterFlow = counterUseCase.counterFlow

    suspend operator fun invoke(): ViewResult<EmailValidation> = coroutineScope {
        val counterJob = async { counterUseCase.startCounter(3) }
        val observeJob = async { authManager.observeEmailValidation() }
        val event = select {
            observeJob.onAwait { result ->
                counterJob.cancel()
                ViewResult.Success(handleResult(result))
            }

            counterJob.onAwait {
                observeJob.cancel()
                ViewResult.Success(EmailValidation.Timeout)
            }
        }
        return@coroutineScope event
    }

    private fun handleResult(result: AppResult<Unit>) =
        result.mapAppResult(
            onSuccess = { EmailValidation.Valid },
            onError = { EmailValidation.Error }
        )

}



