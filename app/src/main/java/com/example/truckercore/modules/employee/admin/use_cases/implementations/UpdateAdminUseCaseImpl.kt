package com.example.truckercore.modules.employee.admin.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CheckAdminExistenceUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.UpdateAdminUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdateAdminUseCaseImpl(
    private val repository: AdminRepository,
    private val checkExistence: CheckAdminExistenceUseCase,
    override val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: AdminMapper,
    override val requiredPermission: Permission
) : UseCase(permissionService), UpdateAdminUseCase {

    override suspend fun execute(user: User, admin: Admin): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) verifyExistence(user, admin)
            else handleUnauthorizedPermission(user, admin.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun verifyExistence(user: User, admin: Admin): Response<Unit> =
        when (val existenceResponse = checkExistence.execute(user, admin.id!!).single()) {
            is Response.Success -> processUpdate(admin)
            is Response.Empty -> handleNonExistentObject(admin.id)
            is Response.Error -> logAndReturnResponse(existenceResponse)
        }

    private suspend fun processUpdate(adminToUpdate: Admin): Response<Unit> {
        validatorService.validateEntity(adminToUpdate)
        val dto = mapper.toDto(adminToUpdate)
        return repository.update(dto).single()
    }

}