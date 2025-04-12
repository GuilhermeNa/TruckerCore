package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailResult
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.Result

/**
 * Interface defining the contract for a use case that creates a new user and verifies their email.
 * This use case involves creating the user, updating their display name, and sending an email verification request.
 */
internal interface CreateUserAndVerifyEmailUseCase {

    /**
     * Main function to create a user and verify their email address.
     * This method performs multiple asynchronous operations: user creation, name update, and email verification.
     *
     * @param credential The credentials (email and password) used to create the user.
     * @return [NewEmailResult] A result containing information about the user creation process,
     *         including whether the user was created successfully, if the name was updated,
     *         if the email was sent, and any errors encountered during the process.
     */
    suspend operator fun invoke(credential: EmailAuthCredential): NewEmailResult

}