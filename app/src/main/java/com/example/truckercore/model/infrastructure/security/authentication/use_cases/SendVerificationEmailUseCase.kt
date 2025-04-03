package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.shared.utils.sealeds.Response

/**
 * Use case interface for sending a verification email to the authenticated Firebase user.
 *
 * This interface defines the contract for a use case that is responsible for sending a verification email
 * to the currently authenticated Firebase user. The actual implementation of this use case is responsible
 * for interacting with the authentication repository to retrieve the current user and send the email.
 */
interface SendVerificationEmailUseCase {

    /**
     * Invokes the use case to send a verification email to the current Firebase user.
     *
     * @return [Response] A response object that indicates the result of the operation. It can either be:
     * - [Response.Success] if the email was sent successfully.
     * - [Response.Error] if there was an error (e.g., no user found).
     */
    suspend operator fun invoke(): Response<Unit>

}