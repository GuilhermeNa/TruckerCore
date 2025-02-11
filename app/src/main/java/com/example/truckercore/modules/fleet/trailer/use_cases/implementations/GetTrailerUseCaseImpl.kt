package com.example.truckercore.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.GetTrailerUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.utils.sealeds.Response.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetTrailerUseCaseImpl(
    private val repository: TrailerRepository,
    private val validatorService: ValidatorService,
    private val mapper: TrailerMapper,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), GetTrailerUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<Trailer>> =
        with(documentParams) {
            user.runIfPermitted { getMappedTrailerFlow(this) }
        }

    private fun getMappedTrailerFlow(documentParams: DocumentParameters) =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Success) {
                Success(validateAndMapToEntity(response.data))
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: TrailerDto): Trailer {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<Trailer>>> =
        with(queryParams) {
            user.runIfPermitted { getMappedTrailerListFlow(queryParams) }
        }

    private fun getMappedTrailerListFlow(queryParams: QueryParameters) =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Success) {
                Success(response.data.map { validateAndMapToEntity(it) })
            } else Response.Empty
        }

}