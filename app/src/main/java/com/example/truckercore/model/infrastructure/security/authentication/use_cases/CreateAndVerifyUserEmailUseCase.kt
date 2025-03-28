package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailUserResponse
import com.example.truckercore.model.shared.utils.sealeds.Response

/**
 * Interface representing the use case for creating and verifying a user via email.
 * This use case is responsible for validating the provided email and password, creating a user
 * in Firebase Authentication, and sending an email verification to the newly created user.
 */
internal interface CreateAndVerifyUserEmailUseCase {

    /**
     * Creates a new user using email and password, and sends an email verification.
     *
     * This method performs the following steps:
     * 1. Validates the provided email and password.
     * 2. Creates the user in Firebase Authentication with the given email and password.
     * 3. If user creation is successful, it sends a verification email.
     * 4. Returns a [Response] containing a [NewEmailUserResponse] that includes information about
     *    the user creation process (whether the user was created, email sent, or errors encountered).
     *
     * If the provided email format is invalid, or if the password does not meet the required
     * criteria (at least 6 characters), the operation will return an error response.
     *
     * @param credential The [EmailAuthCredential] containing the user's email and password.
     * @return A [NewEmailUserResponse] that includes the result of the user creation and email verification process.
     *         If the operation fails at any step (invalid input, database issue, etc.), it returns an error response.
     */
    suspend operator fun invoke(credential: EmailAuthCredential): NewEmailUserResponse

}