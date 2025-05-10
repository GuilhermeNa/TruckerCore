package com.example.truckercore.model.modules.user.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.modules.user.specification.UserSpec
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.getOrReturn

class GetUserUserCaseImpl(
    private val dataRepository: DataRepository
) : GetUserUserCase {

    override suspend fun invoke(spec: UserSpec): AppResponse<User> =
        try {
            val dto = dataRepository.findOneBy(spec).getOrReturn { return it }
            val entity = UserMapper.toEntity(dto)
            AppResponse.Success(entity)
        } catch (e: Exception) {
            AppResponse.Error(e)
        }

}