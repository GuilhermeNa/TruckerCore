package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.DeleteBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

internal class DeleteBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val checkExistence: CheckBusinessCentralExistenceUseCase,
    private val permissionService: PermissionService
) : DeleteBusinessCentralUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result = if (userHasPermission(user)) {
            val response = checkEntityExists(user, id)
            if (response is Response.Success) {
                handleCheckExistenceSuccess(id, response)
            } else handleCheckExistenceFailure(response)
        } else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.DELETE_BUSINESS_CENTRAL)

    private suspend fun checkEntityExists(user: User, id: String) =
        checkExistence.execute(user, id).single()

    private suspend fun handleCheckExistenceSuccess(
        id: String,
        response: Response.Success<Boolean>
    ): Response<Unit> =
        if (response.data) handleExistentObject(id)
        else handleNonExistentObject(id)

    private suspend fun handleExistentObject(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

    private fun handleNonExistentObject(id: String): Response.Error {
        logError("${javaClass.simpleName}: Trying to delete a non-existent object.")
        return Response.Error(
            ObjectNotFoundException("Trying to delete a non-existent object with id: $id")
        )
    }

    private fun handleCheckExistenceFailure(response: Response<Boolean>): Response<Unit> {
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
        val message = "Unauthorized access attempt by user ID: ${user.id}, for BusinessCentral ID: $id"
        logWarn("${javaClass.simpleName}: $message")
        return Response.Error(UnauthorizedAccessException(message))
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred while removing a BusinessCentral during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}