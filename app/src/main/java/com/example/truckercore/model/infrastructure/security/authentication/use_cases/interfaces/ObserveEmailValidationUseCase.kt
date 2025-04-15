package com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepository
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import kotlinx.coroutines.flow.Flow

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
    operator fun invoke(): Flow<AppResult<Unit>>

}