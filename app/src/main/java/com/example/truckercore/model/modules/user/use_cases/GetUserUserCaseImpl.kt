package com.example.truckercore.model.modules.user.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.modules.user.specification.UserSpec
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.mapAppResponse

class GetUserUserCaseImpl(
    private val dataRepository: DataRepository
) : GetUserUserCase {

    override suspend fun invoke(spec: UserSpec): AppResponse<User> {
        return dataRepository.findOneBy(spec).mapAppResponse(
            onSuccess = { getSuccessResponse(it) },
            onEmpty = { AppResponse.Empty },
            onError = { AppResponse.Error(it) }
        )
    }

    private fun getSuccessResponse(dto: UserDto): AppResponse.Success<User> {
        val user = UserMapper.toEntity(dto)
        return AppResponse.Success(user)
    }

}