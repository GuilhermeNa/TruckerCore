package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.shared.utils.sealeds.Result

internal class SendVerificationEmailUseCaseImpl(
    private val authRepository: FirebaseAuthRepository
) : SendVerificationEmailUseCase {

    override suspend fun invoke(): Result<Unit> =
        authRepository.getCurrentUser()?.let { fbUser ->
            authRepository.sendEmailVerification(fbUser)
        } ?: Result.Error(NullFirebaseUserException(ERROR_MESSAGE))

    companion object {
        private const val ERROR_MESSAGE = "Failed to complete email verification." +
                " The Firebase current user was not found"
    }

}