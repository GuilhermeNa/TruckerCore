package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailUserResponse
import com.example.truckercore.model.shared.errors.InvalidResponseException
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.model.shared.utils.expressions.logInfo
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class CreateUserAndVerifyEmailUseCaseImpl(
    private val authRepository: FirebaseAuthRepository
) : CreateUserAndVerifyEmailUseCase {

    private lateinit var fbUser: FirebaseUser
    private val logger = MLogger()

    override suspend fun invoke(credential: EmailAuthCredential): NewEmailUserResponse {

        // Create an new user. Early return an default Response when fails.
        val userResp = try {
            createUser(credential)
        } catch (e: Exception) {
            return buildResponseWhenUserCreationFailed(e)
        }

        // Async name update and email send
        val nameAndEmailResp = updateNameAndSendEmailAsync(credential)
        val combinedResp = CombinedResponses(
            userResp = userResp,
            nameResp = nameAndEmailResp.nameResp,
            emailResp = nameAndEmailResp.emailResp
        )

        // Get all responses and combine
        buildFinalResponse(combinedResp)

    }

    private fun buildResponseWhenUserCreationFailed(e: Exception) =
        NewEmailUserResponse(_createUserError = e)

    private suspend fun createUser(credential: EmailAuthCredential): Response<FirebaseUser> {
        val response = authRepository.createUserWithEmail(credential.email, credential.name)

        when (response) {
            is Response.Success -> {
                fbUser = response.data
                logInfo("User created with success for email: ${credential.email}.")
            }

            is Response.Empty -> {
                val message = "Failure occurs while creating a new User access with email: " +
                        "${credential.email}. Some error ocurred on firebase."
                logError(message)
                throw InvalidResponseException(message)
            }

            is Response.Error -> {
                val message = "Wrong response found while creating a new User with email: " +
                        "${credential.email}. Received an Empty response and it is not allowed."
                logError(message)
                throw response.exception
            }
        }

        return response
    }

    private fun buildResponseWhenFailed() = NewEmailUserResponse()

    private suspend fun updateNameAndSendEmailAsync(credential: EmailAuthCredential) =
        coroutineScope {
            val nameDef = async { updateName(credential) }
            val emailDef = async { sendEmail(credential) }

            return@coroutineScope NameAndEmailResp(
                nameResp = nameDef.await(),
                emailResp = emailDef.await()
            )
        }

    private suspend fun updateName(credential: EmailAuthCredential): Response<Unit> {
        val request = userProfileChangeRequest { displayName = credential.name }
        val response = authRepository.updateUserProfile(fbUser, request)

        if (response is Response.Success) {
            logger.logNameUpdated(credential.name)
        } else logger.logNameFailed(response, credential.name)

        return response
    }

    private suspend fun sendEmail(credential: EmailAuthCredential): Response<Unit> {
        val response = authRepository.sendEmailVerification(fbUser)

        if (response is Response.Success) {
            logger.logEmailSent(credential.email)
        } else logger.logEmailFailed(response, credential.email)

        return response
    }

    private fun buildFinalResponse(combinedResp: CombinedResponses): NewEmailUserResponse =
        with(combinedResp) {
            return NewEmailUserResponse(
                user = fbUser,
                userCreated = userResp.isSuccess(),
                nameUpdated = nameResp.isSuccess(),
                emailSent = emailResp.isSuccess(),
                _createUserError = userResp.extractException(),
                updateNameError = nameResp.extractException(),
                sendEmailError = emailResp.extractException()
            )
        }

    // Helper Classes ------------------------------------------------------------------------------
    private class MLogger {

        fun logUserCreationError(userResp: Response<FirebaseUser>, email: String) {
            val (cause, message) = when (userResp) {


                else -> Pair(
                    InvalidResponseException(""),
                    "Wrong response found while creating a new User with email: $email. " +
                            "Received an Empty response and it is not allowed."
                )
            }

            logError("$message cause: $cause")
        }

        fun logUserCreationSuccess(email: String) {

        }

        fun logNameUpdated(name: String) {
            logInfo("User name have been updated successfully: $name.")
        }

        fun logNameFailed(nameResp: Response<Unit>, name: String) {
            val (cause, message) = when (nameResp) {
                is Response.Error -> Pair(
                    nameResp.exception,
                    "Failure occurs while updating the user's name: $name." +
                            " Some error ocurred on firebase."
                )

                else -> Pair(
                    InvalidResponseException(),
                    "Wrong response found while while updating the user's name: $name. " +
                            "Received an Empty response and it is not allowed."
                )
            }

            logError("$message cause: $cause")
        }

        fun logEmailSent(email: String) {
            logInfo("Verification email was sent successfully: $email.")
        }

        fun logEmailFailed(response: Response<Unit>, email: String) {
            val (cause, message) = when (response) {
                is Response.Error -> Pair(
                    response.exception,
                    "Failure occurs while sending verification email: $email." +
                            " Some error ocurred on firebase."
                )

                else -> Pair(
                    InvalidResponseException(),
                    "Wrong response found while while sending verification email: $email. " +
                            "Received an Empty response and it is not allowed."
                )
            }

            logError("$message cause: $cause")
        }

        fun logUnknownError(e: Exception) {
            logError("An unknown error ocurred, cause: $e")
        }

    }

    private data class CombinedResponses(
        val userResp: Response<FirebaseUser>,
        val nameResp: Response<Unit>,
        val emailResp: Response<Unit>
    )

    private data class NameAndEmailResp(
        val nameResp: Response<Unit>,
        val emailResp: Response<Unit>
    )

}

