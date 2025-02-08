package com.example.truckercore.modules.employee.admin.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminByIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetAdminByIdUseCaseImpl(
    private val repository: AdminRepository,
    override val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: AdminMapper,
    override val requiredPermission: Permission
) : UseCase(permissionService), GetAdminByIdUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Admin>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) fetchAdminById(id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun fetchAdminById(id: String): Response<Admin> =
        when (val response = repository.fetchById(id).single()) {
            is Response.Success -> processResponse(response)
            is Response.Error -> logAndReturnResponse(response)
            is Response.Empty -> response
        }

    private fun processResponse(response: Response.Success<AdminDto>): Response<Admin> {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        return Response.Success(entity)
    }

}