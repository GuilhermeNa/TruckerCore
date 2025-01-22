package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.UpdateUserUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdateUserUseCaseImpl(
    private val repository: UserRepository,
    private val checkExistence: CheckUserExistenceUseCase,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper
) : UseCase(), UpdateUserUseCase {

    override suspend fun execute(user: User, userToUpdate: User): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) continueForExistenceCheckage(user, userToUpdate)
            else handleUnauthorizedPermission(user, userToUpdate.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.UPDATE_USER)

    private suspend fun continueForExistenceCheckage(user: User, userToUpdate: User) =
        when (
            val existenceResponse = checkExistence.execute(user, userToUpdate.id!!).single()
        ) {
            is Response.Success -> updateUser(userToUpdate)
            is Response.Empty -> handleNonExistentObject(userToUpdate.id)
            is Response.Error -> handleFailureResponse(existenceResponse)
        }

    private suspend fun updateUser(userToUpdate: User): Response<Unit> {
        validatorService.validateEntity(userToUpdate)
        val dto = mapper.toDto(userToUpdate)
        return repository.update(dto).single()
    }

}