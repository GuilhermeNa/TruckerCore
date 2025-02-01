package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserByIdUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
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
) : UseCase(), GetUserByIdUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<User>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) processResponse(id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_USER)

    private suspend fun processResponse(id: String): Response<User> {
        return when (val response = repository.fetchById(id).single()) {
            is Response.Success -> handleSuccessResponse(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }
    }

    private fun handleSuccessResponse(response: Response.Success<UserDto>): Response<User> {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        return Response.Success(entity)
    }

}