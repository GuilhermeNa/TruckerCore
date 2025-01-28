package com.example.truckercore.modules.employee.admin.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CreateAdminUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateAdminUseCaseImpl(
    private val repository: AdminRepository,
    private val validatorService: ValidatorService,
    private val permissionService: PermissionService,
    private val mapper: AdminMapper
) : UseCase(), CreateAdminUseCase {

    override suspend fun execute(user: User, admin: Admin): Flow<Response<String>> = flow {
        val result =
            if (userHasPermission(user)) processCreation(admin)
            else handleUnauthorizedPermission(user, admin.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.CREATE_ADMIN)

    private suspend fun processCreation(admin: Admin): Response<String> {
        validatorService.validateForCreation(admin)
        val adminDto = mapper.toDto(admin)
        return repository.create(adminDto).single()
    }

}