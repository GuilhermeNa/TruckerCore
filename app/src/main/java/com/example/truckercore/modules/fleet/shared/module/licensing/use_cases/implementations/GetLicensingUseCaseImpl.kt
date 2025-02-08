package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.NewLicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetLicensingUseCaseImpl(
    private val repository: LicensingRepository,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission,
    private val validatorService: ValidatorService,
    private val mapper: NewLicensingMapper
) : UseCase(permissionService), GetLicensingUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<Licensing>> =
        with(documentParams) {
            user.runIfPermitted { getMappedLicensingFlow(this) }
        }

    private fun getMappedLicensingFlow(documentParams: DocumentParameters): Flow<Response<Licensing>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                Response.Success(validateAndMapToEntity(response.data))
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: LicensingDto): Licensing {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<Licensing>>> =
        with(queryParams) {
            user.runIfPermitted { getMappedLicensingListFlow(queryParams) }
        }

    private fun getMappedLicensingListFlow(queryParams: QueryParameters): Flow<Response<List<Licensing>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                Response.Success(response.data.map { validateAndMapToEntity(it) })
            } else Response.Empty
        }

}