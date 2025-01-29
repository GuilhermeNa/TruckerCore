package com.example.truckercore.modules.fleet.truck.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.DeleteTruckUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class DeleteTruckUseCaseImpl(
    private val repository: TruckRepository,
    private val checkExistence: CheckTruckExistenceUseCase,
    private val permissionService: PermissionService
) : UseCase(), DeleteTruckUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) verifyExistence(user, id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.DELETE_TRUCK)

    private suspend fun verifyExistence(user: User, id: String) =
        when (val existenceResponse = checkExistence.execute(user, id).single()) {
            is Response.Success -> delete(id)
            is Response.Empty -> handleNonExistentObject(id)
            is Response.Error -> handleFailureResponse(existenceResponse)
        }

    private suspend fun delete(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

}