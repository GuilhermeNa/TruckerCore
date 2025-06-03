package com.example.truckercore.model.modules.aggregation.system_access.use_cases.implementations

import com.example.truckercore._shared.classes.AppResult
import com.example.truckercore._shared.expressions.mapAppResponse
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.aggregation.system_access.use_cases.interfaces.IsUserRegisteredInSystemUseCase
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.user.specification.UserSpec

class IsUserRegisteredInSystemUseCaseImpl(
    private val repository: DataRepository
) : IsUserRegisteredInSystemUseCase {

    override suspend fun invoke(uid: UID): AppResult<Boolean> {
        val spec = UserSpec(uid = uid)
        val response = repository.findOneBy(spec)
        return response.mapAppResponse(
            onSuccess = { AppResult.Success(true) },
            onEmpty = { AppResult.Success(false) },
            onError = { AppResult.Error(it) }
        )
    }

}