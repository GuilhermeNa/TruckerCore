package com.example.truckercore.model.infrastructure.integration._auth.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository

interface ThereIsLoggedUserUseCase {

    /**
     * Use case for checking if there is a logged user.
     *
     * @return true when there is an active session, false if not.
     *
     * @see [AuthenticationRepository.getCurrentUser]
     */
    operator fun invoke(): Boolean

}