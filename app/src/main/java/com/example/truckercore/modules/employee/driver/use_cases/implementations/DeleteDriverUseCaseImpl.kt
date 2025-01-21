package com.example.truckercore.modules.employee.driver.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.DeleteDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class DeleteDriverUseCaseImpl(
    private val repository: DriverRepository,
    private val checkExistence: CheckUserExistenceUseCase,
    private val permissionService: PermissionService
) : DeleteDriverUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) continueForExistenceCheckage(user, id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun continueForExistenceCheckage(user: User, id: String) =
        when (val existenceResponse = checkExistence.execute(user, id).single()) {
            is Response.Success -> existenceResponse.data.let { driverExists ->
                if (driverExists) deleteDriver(id)
                else handleNonExistentObject(id)
            }

            else -> handleExistenceCheckageFailure(existenceResponse)
        }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.DELETE_DRIVER)

    private suspend fun deleteDriver(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

    private fun handleNonExistentObject(id: String): Response.Error {
        logError("${javaClass.simpleName}: Trying to delete a non-existent object.")
        return Response.Error(
            ObjectNotFoundException("Trying to delete a non-existent driver with id: $id")
        )
    }

    private fun handleExistenceCheckageFailure(response: Response<Boolean>): Response<Unit> {
        return if (response is Response.Error) {
            logError("${javaClass.simpleName}: Received an error from CheckExistenceUseCase.")
            Response.Error(response.exception)
        } else {
            val message = "The CheckExistenceUseCase response came back empty."
            logError("${javaClass.simpleName}: $message")
            Response.Error(ObjectNotFoundException(message))
        }
    }

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        val message = "Unauthorized access attempt by user ID: ${user.id}, for Driver ID: $id"
        logWarn("${javaClass.simpleName}: $message")
        return Response.Error(UnauthorizedAccessException(message))
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred while removing a Driver during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}