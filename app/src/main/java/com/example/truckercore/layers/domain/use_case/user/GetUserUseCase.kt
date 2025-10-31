package com.example.truckercore.layers.domain.use_case.user

import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data.base.mapper.impl.UserMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification.specs_impl.UserSpec
import com.example.truckercore.layers.data.repository.data.DataRepository
import com.example.truckercore.layers.domain.model.user.User


interface GetUserUserCase {

    suspend operator fun invoke(spec: UserSpec): DataOutcome<User>

}

class GetUserUserCaseImpl(
    private val dataRepository: DataRepository
) : GetUserUserCase {

    override suspend fun invoke(spec: UserSpec): DataOutcome<User> = TODO()
   /*     try {

            dataRepository.findOneBy(spec)
                .getOrElse { return it }
                .let { getSuccessResponse(it) }

        } catch (e: com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException) {
            DataOutcome.Failure(e)
        } catch (e: Exception) {
            DataOutcome.Failure(
               TechnicalException.Unknown("$UNEXPECTED_ERROR_MESSAGE $spec", e)
            )
        }*/

    private fun getSuccessResponse(dto: UserDto): DataOutcome.Success<User> {
        val user = UserMapper.toEntity(dto)
        return DataOutcome.Success(user)
    }

    companion object {
        private const val UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred while fetching the user."
    }

}