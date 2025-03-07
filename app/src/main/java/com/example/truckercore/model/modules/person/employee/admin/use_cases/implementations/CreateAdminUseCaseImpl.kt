package com.example.truckercore.model.modules.person.employee.admin.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.model.modules.person.employee.admin.repository.AdminRepository
import com.example.truckercore.model.modules.person.employee.admin.use_cases.interfaces.CreateAdminUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreateAdminUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: AdminRepository,
    private val validatorService: ValidatorService,
    private val mapper: AdminMapper,
) : UseCase(permissionService),
    CreateAdminUseCase {

    override fun execute(user: User, admin: Admin): Flow<Response<String>> =
        user.runIfPermitted { processCreation(admin) }

    private fun processCreation(admin: Admin): Flow<Response<String>> {
        validatorService.validateForCreation(admin)
        val adminDto = mapper.toDto(admin)
        return repository.create(adminDto)
    }

}