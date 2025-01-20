package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserByIdUseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetUserByIdUseCaseImpl(
    private val repository: UserRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper
) : GetUserByIdUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<User>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) {
                val response = repository.fetchById(id).single()
                processResponse(response)
            } else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_USER)

    private fun processResponse(response: Response<UserDto>): Response<User> {
        return when (response) {
            is Response.Success -> handleSuccessResponse(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> handleEmptyResponse(response)
        }
    }

    private fun handleSuccessResponse(response: Response.Success<UserDto>): Response<User> {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        return Response.Success(entity)
    }

    private fun handleFailureResponse(response: Response.Error): Response.Error {
        logError("${javaClass.simpleName}: Received an error from database.")
        return response
    }

    private fun handleEmptyResponse(response: Response.Empty): Response.Empty {
        logWarn("${javaClass.simpleName}: The database response came back empty.")
        return response
    }

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        val message = "Unauthorized access attempt by user: ${user.id}, for User ID: $id."
        logWarn("${javaClass.simpleName}: $message")
        return Response.Error(UnauthorizedAccessException(message))
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${javaClass.simpleName}: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}