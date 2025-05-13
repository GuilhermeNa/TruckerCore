package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore._utils.classes.AppResult

interface ObserveEmailValidationUseCase {

    /**
     * Observes whether the authenticated user's email has been verified.
     * @return A Flow emitting:
     *
     * [AppResult.Success] if the email has been verified
     *
     * [AppResult.Error] with ObserveEmailValidationErrCode on failure
     *
     * @see [AuthenticationRepository.observeEmailValidation]
     */
    suspend operator fun invoke(): AppResult<Unit>

}