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
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateAdminUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: AdminRepository,
    private val checkExistence: CheckAdminExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: AdminMapper
) : UseCase(permissionService), UpdateAdminUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, admin: Admin): Flow<Response<Unit>> {
        val id = admin.id ?: throw NullPointerException("Null Admin id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) throw ObjectNotFoundException(
                "Attempting to update a Admin that was not found for id: $id."
            )
            user.runIfPermitted { processUpdate(admin) }
        }
    }

    private fun processUpdate(adminToUpdate: Admin): Flow<Response<Unit>> {
        validatorService.validateEntity(adminToUpdate)
        val dto = mapper.toDto(adminToUpdate)
        return repository.update(dto)
    }

}