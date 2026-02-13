package com.example.truckercore.layers.domain.use_case.authentication

import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository

class GetUserNameUseCase(
    private val repository: AuthenticationRepository
) {

    operator fun invoke(): DataOutcome<Name> =
        repository.getUserName()

}