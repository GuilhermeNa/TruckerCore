package com.example.truckercore.modules.employee.admin.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CheckAdminExistenceUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.DeleteAdminUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class DeleteAdminUseCaseImpl(
    private val repository: AdminRepository,
    private val checkExistence: CheckAdminExistenceUseCase,
    private val permissionService: PermissionService
) : UseCase(), DeleteAdminUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) continueForExistenceCheckage(user, id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.DELETE_ADMIN)

    private suspend fun continueForExistenceCheckage(user: User, id: String) =
        when (
            val existenceResponse = checkExistence.execute(user, id).single()
        ) {
            is Response.Success -> deleteAdmin(id)
            is Response.Empty -> handleNonExistentObject(id)
            is Response.Error -> handleFailureResponse(existenceResponse)
        }

    private suspend fun deleteAdmin(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

}