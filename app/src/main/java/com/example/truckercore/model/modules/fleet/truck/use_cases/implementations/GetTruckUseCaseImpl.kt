package com.example.truckercore.model.modules.fleet.truck.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.model.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.GetTruckUseCase
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetTruckUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: TruckRepository,
    private val validatorService: ValidatorService,
    private val mapper: TruckMapper
) : UseCase(permissionService), GetTruckUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<Truck>> =
        with(documentParams) {
            user.runIfPermitted { getTruckFlow(this) }
        }

    private fun getTruckFlow(documentParams: DocumentParameters) =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                val result = validateAndMapToEntity(response.data)
                Response.Success(result)
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: TruckDto): Truck {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<Truck>>> =
        with(queryParams) {
            user.runIfPermitted { getTruckListFlow(queryParams) }
        }

    private fun getTruckListFlow(queryParams: QueryParameters) =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                val result = response.data.map { validateAndMapToEntity(it) }
                Response.Success(result)
            } else Response.Empty
        }

}