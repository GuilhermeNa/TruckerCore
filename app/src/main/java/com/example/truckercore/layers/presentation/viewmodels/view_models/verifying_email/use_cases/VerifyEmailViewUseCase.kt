package com.example.truckercore.layers.presentation.viewmodels.view_models.verifying_email.use_cases

import com.example.truckercore.core.expressions.mapAppResult
import com.example.truckercore.data.modules.authentication.manager.AuthManager
import com.example.truckercore.domain._shared.helpers.ViewError
import com.example.truckercore.domain._shared.helpers.ViewResult
import com.example.truckercore.domain._shared.use_cases.CounterUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.selects.select

class VerifyEmailViewUseCase(
    private val counterUseCase: CounterUseCase,
    private val authManager: AuthManager
) {

    val counterFlow = counterUseCase.counterFlow

    suspend operator fun invoke(): ViewResult<Unit> = coroutineScope {
        val counterJob = async { counterUseCase.startCounter(59) }
        val observeJob = async { authManager.observeEmailValidation() }
        val event = select {
            observeJob.onAwait { result ->
                counterJob.cancel()
                handleResult(result)
            }

            counterJob.onAwait {
                observeJob.cancel()
                ViewResult.Error(ViewError.Recoverable("Timeout"))
            }
        }
        return@coroutineScope event
    }

    private fun handleResult(result: AppResult<Unit>) =
        result.mapAppResult(
            onSuccess = { ViewResult.Success(Unit) },
            onError = { ViewResult.Error(ViewError.Critical) }
        )

}



