package com.example.truckercore.modules.employee.admin.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetAdminUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: AdminRepository,
    private val validatorService: ValidatorService,
    private val mapper: AdminMapper
) : UseCase(permissionService), GetAdminUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<Admin>> =
        with(documentParams) {
            user.runIfPermitted { getMappedAdminFlow(this) }
        }

    private fun getMappedAdminFlow(documentParams: DocumentParameters): Flow<Response<Admin>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                Response.Success(validateAndMapToEntity(response.data))
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: AdminDto): Admin {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

}