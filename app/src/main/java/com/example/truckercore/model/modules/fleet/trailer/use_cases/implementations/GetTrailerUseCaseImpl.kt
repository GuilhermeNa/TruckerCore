package com.example.truckercore.model.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.model.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.GetTrailerUseCase
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.AppResponse.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetTrailerUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: TrailerRepository,
    private val validatorService: ValidatorService,
    private val mapper: TrailerMapper
) : UseCase(permissionService), GetTrailerUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<AppResponse<Trailer>> =
        with(documentParams) {
            user.runIfPermitted { getTrailerFlow(this) }
        }

    private fun getTrailerFlow(documentParams: DocumentParameters) =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Success) {
                Success(validateAndMapToEntity(response.data))
            } else AppResponse.Empty
        }

    private fun validateAndMapToEntity(dto: TrailerDto): Trailer {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<AppResponse<List<Trailer>>> =
        with(queryParams) {
            user.runIfPermitted { getTrailerListFlow(queryParams) }
        }

    private fun getTrailerListFlow(queryParams: QueryParameters) =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Success) {
                Success(response.data.map { validateAndMapToEntity(it) })
            } else AppResponse.Empty
        }

}