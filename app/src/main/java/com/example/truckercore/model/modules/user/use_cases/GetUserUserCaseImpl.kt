package com.example.truckercore.model.modules.user.use_cases

import com.example.truckercore.model.errors.exceptions.AppException
import com.example.truckercore.model.errors.exceptions.technical.TechnicalException
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.modules.user.specification.UserSpec
import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.expressions.getOrReturn

class GetUserUserCaseImpl(
    private val dataRepository: DataRepository
) : GetUserUserCase {

    override suspend fun invoke(spec: UserSpec): AppResponse<User> =
        try {

            dataRepository.findOneBy(spec)
                .getOrReturn { return it }
                .let { getSuccessResponse(it) }

        } catch (e: AppException) {
            AppResponse.Error(e)
        } catch (e: Exception) {
            AppResponse.Error(
                TechnicalException.Unknown("$UNEXPECTED_ERROR_MESSAGE $spec", e)
            )
        }

    private fun getSuccessResponse(dto: UserDto): AppResponse.Success<User> {
        val user = UserMapper.toEntity(dto)
        return AppResponse.Success(user)
    }

    companion object {
        private const val UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred while fetching the user."
    }

}