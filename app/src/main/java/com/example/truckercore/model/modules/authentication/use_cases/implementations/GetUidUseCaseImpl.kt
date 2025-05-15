package com.example.truckercore.model.modules.authentication.use_cases.implementations

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.infrastructure.integration.auth.for_app.repository.AuthenticationRepository
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.authentication.use_cases.interfaces.GetUidUseCase

class GetUidUseCaseImpl(
    private val repository: AuthenticationRepository
) : GetUidUseCase {

    override fun invoke(): AppResult<UID> =
        repository.getUid()

}