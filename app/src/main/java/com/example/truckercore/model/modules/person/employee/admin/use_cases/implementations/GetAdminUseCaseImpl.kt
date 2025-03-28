package com.example.truckercore.model.modules.person.employee.admin.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.model.modules.person.employee.admin.repository.AdminRepository
import com.example.truckercore.model.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
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
            user.runIfPermitted { getAdminFlow(this) }
        }

    private fun getAdminFlow(documentParams: DocumentParameters): Flow<Response<Admin>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                val result = validateAndMapToEntity(response.data)
                Response.Success(result)
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: AdminDto): Admin {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<Admin>>> =
        with(queryParams) {
            user.runIfPermitted { getAdminListFlow(this) }
        }

    private fun getAdminListFlow(queryParams: QueryParameters): Flow<Response<List<Admin>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                val result = response.data.map { validateAndMapToEntity(it) }
                Response.Success(result)
            } else Response.Empty
        }

}