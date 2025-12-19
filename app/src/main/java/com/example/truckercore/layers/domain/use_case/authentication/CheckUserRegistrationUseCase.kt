package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.core.my_lib.expressions.get
import com.example.truckercore.layers.domain.base.enums.RegistrationStatus

interface CheckUserRegistrationUseCase {

    operator fun invoke(): DataOutcome<RegistrationStatus>

}

class CheckUserRegistrationUseCaseImpl(
    private val hasLoggedUser: HasLoggedUserUseCase,
    private val isEmailVerified: IsEmailVerifiedUseCase,
    private val isProfileComplete: IsProfileCompleteUseCase
) : CheckUserRegistrationUseCase {

    override operator fun invoke(): DataOutcome<RegistrationStatus> {

        // Check if there is a logged-in user
        val userNotFound = !hasLoggedUser().get()
        if (userNotFound) {
            val status = RegistrationStatus.ACCOUNT_NOT_FOUND
            return DataOutcome.Success(status)
        }

        // Check if the user's email has been verified
        val emailNotVerified = !isEmailVerified().get()
        if (emailNotVerified) {
            val status = RegistrationStatus.EMAIL_NOT_VERIFIED
            return DataOutcome.Success(status)
        }

        // Check if the user's profile information is complete
        val profileIncomplete = !isProfileComplete().get()
        if (profileIncomplete) {
            val status = RegistrationStatus.MISSING_PROFILE
            return DataOutcome.Success(status)
        }

        // All checks passed — the registration process is complete
        return DataOutcome.Success(RegistrationStatus.COMPLETE)

    }

}

