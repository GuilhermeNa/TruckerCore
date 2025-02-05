package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CheckLicensingExistenceUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.expressions.handleUnexpectedError
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CheckLicensingExistenceUseCaseImpl(
    private val repository: LicensingRepository,
    private val permissionService: PermissionService
) : UseCase(), CheckLicensingExistenceUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) verifyExistence(id)
            else handleUnauthorizedPermission(user, id)

        emit(result)
    }.catch {
        emit(it.handleUnexpectedError())
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_LICENSING)

    private suspend fun verifyExistence(id: String): Response<Unit> =
        when (val response = repository.entityExists(id).single()) {
            is Response.Error -> handleFailureResponse(response)
            else -> response
        }

}