package com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.shared.utils.sealeds.AppResult

interface SendVerificationEmailUseCase {

    /**
     * Use case for sending a verification email to the current user.
     *
     * @return [AppResult.Success] if the email was sent.
     *
     * [AppResult.Error] if the user is not logged in, or a network/server error occurs.
     *
     * @see [AuthenticationRepository.sendEmailVerification]
     */
    suspend operator fun invoke(): AppResult<Unit>

}