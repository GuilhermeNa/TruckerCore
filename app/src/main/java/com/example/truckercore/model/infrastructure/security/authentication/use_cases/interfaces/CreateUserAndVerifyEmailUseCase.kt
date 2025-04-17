package com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailResult
import com.example.truckercore.model.infrastructure.security.authentication.repository.AuthenticationRepository

internal interface CreateUserAndVerifyEmailUseCase {

    /**
     * This method performs multiple asynchronous operations: user creation, name update, and email verification.
     *
     * @param credential The credentials (email and password) used to create the user.
     * @return [NewEmailResult] A result containing information about the user creation task.
     *
     * @see [AuthenticationRepository.createUserWithEmail]
     * @see [AuthenticationRepository.updateUserProfile]
     * @see [AuthenticationRepository.sendEmailVerification]
     */
    suspend operator fun invoke(credential: EmailCredential): NewEmailResult

}