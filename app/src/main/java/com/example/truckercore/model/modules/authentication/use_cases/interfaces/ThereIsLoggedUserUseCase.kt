package com.example.truckercore.model.modules.authentication.use_cases.interfaces

import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository

interface ThereIsLoggedUserUseCase {

    /**
     * Use case for checking if there is a logged user.
     *
     * @return true when there is an active session, false if not.
     *
     * @see [AuthenticationRepository.thereIsLoggedUser]
     */
    operator fun invoke(): Boolean

}