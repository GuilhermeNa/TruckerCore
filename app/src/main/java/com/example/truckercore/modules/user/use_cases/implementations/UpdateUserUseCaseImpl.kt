package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.UpdateUserUseCase
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
) : UpdateUserUseCase {

    override suspend fun execute(user: User, userToUpdate: User): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) continueForExistenceCheckage(user, userToUpdate)
            else handleUnauthorizedPermission(user, userToUpdate.id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun continueForExistenceCheckage(user: User, userToUpdate: User) =
        when (val existenceResponse = checkEntityExists(user, userToUpdate)) {
            is Response.Success -> existenceResponse.data.let { userExists ->
                if (userExists) updateUser(userToUpdate)
                else handleNonExistentObject(userToUpdate)
            }

            else -> handleExistenceCheckageFailure(existenceResponse)
        }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.UPDATE_USER)

    private suspend fun checkEntityExists(user: User, userToUpdate: User) =
        checkExistence.execute(user, userToUpdate.id!!).single()

    private suspend fun updateUser(userToUpdate: User): Response<Unit> {
        validatorService.validateEntity(userToUpdate)
        val dto = mapper.toDto(userToUpdate)
        return repository.update(dto).single()
    }

    private fun handleExistenceCheckageFailure(response: Response<Boolean>): Response<Unit> {
        return if (response is Response.Error) {
            logError("${javaClass.simpleName}: Received an error from CheckExistenceUseCase.")
            Response.Error(response.exception)
        } else {
            logError("${javaClass.simpleName}: The CheckExistenceUseCase response came back empty.")
            Response.Empty
        }
    }

    private fun handleNonExistentObject(userToUpdate: User): Response.Error {
        val message = "Trying to update a non-persisted object: $userToUpdate."
        logError("${javaClass.simpleName}: $message")
        return Response.Error(ObjectNotFoundException(message))
    }

    private fun handleUnauthorizedPermission(user: User, id: String?): Response.Error {
        val message = "Unauthorized access attempt by user: ${user.id}, for User ID: $id."
        logWarn("${javaClass.simpleName}: $message")
        return Response.Error(UnauthorizedAccessException(message))
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred during execution: $throwable.")
        return Response.Error(exception = throwable as Exception)
    }

}