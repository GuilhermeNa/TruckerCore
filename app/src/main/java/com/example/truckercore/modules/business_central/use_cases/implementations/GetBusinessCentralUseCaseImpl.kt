package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetBusinessCentralUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: BusinessCentralRepository,
    private val validatorService: ValidatorService,
    private val mapper: BusinessCentralMapper,
) : UseCase(permissionService), GetBusinessCentralUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<BusinessCentral>> =
        with(documentParams) {
            user.runIfPermitted { getMappedBusinessCentralFlow(this) }
        }

    private fun getMappedBusinessCentralFlow(documentParams: DocumentParameters) =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                Response.Success(validateAndMapToEntity(response.data))
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: BusinessCentralDto): BusinessCentral {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<BusinessCentral>>> =
        with(queryParams) {
            user.runIfPermitted { getMappedBusinessCentralListFlow(queryParams) }
        }

    private fun getMappedBusinessCentralListFlow(queryParams: QueryParameters) =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                Response.Success(response.data.map { validateAndMapToEntity(it) })
            } else Response.Empty
        }

}