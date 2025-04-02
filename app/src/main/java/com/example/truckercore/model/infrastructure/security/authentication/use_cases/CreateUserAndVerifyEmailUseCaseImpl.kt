package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailUserResponse
import com.example.truckercore.model.shared.errors.InvalidResponseException
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser

internal class CreateUserAndVerifyEmailUseCaseImpl(
    private val authRepository: FirebaseAuthRepository
) : CreateUserAndVerifyEmailUseCase {

    override suspend fun invoke(credential: EmailAuthCredential) = with(credential) {

        // Check New User Response and get firebase User or exit if got an error
        val newUserResponse = authRepository.createUserWithEmail(email, password)
        val fbUser = if (newUserResponse is Response.Success) newUserResponse.data
        else return@with handleUnexpectedUserResponse(newUserResponse)

        // Check if the email verification have been send when newUser response is success
        val verificationResponse = authRepository.sendEmailVerification(fbUser)
        if (verificationResponse !is Response.Success)
            return@with handleUnexpectedVerificationResponse(fbUser, verificationResponse)

        return NewEmailUserResponse(
            user = fbUser,
            userCreated = true,
            emailSent = true
        )

    }

    private fun handleUnexpectedUserResponse(newUserResponse: Response<FirebaseUser>) =
        NewEmailUserResponse(
            userCreated = false,
            emailSent = false,
            _createUserError = try {
                newUserResponse.extractException()
            } catch (_: Exception) {
                InvalidResponseException("Database returned an unexpected response: (Empty Response)")
            }
        )

    private fun handleUnexpectedVerificationResponse(
        fbUser: FirebaseUser,
        newUserResponse: Response<Unit>
    ) = NewEmailUserResponse(
        user = fbUser,
        userCreated = true,
        emailSent = false,
        sendEmailError = try {
            newUserResponse.extractException()
        } catch (_: Exception) {
            InvalidResponseException("Database returned an unexpected response: (Empty Response)")
        }
    )

}