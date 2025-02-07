package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.handleUnexpectedError
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetLicensingUseCaseImpl(
    private val repository: LicensingRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: LicensingMapper
) : UseCase(), GetLicensingUseCase {

    override suspend fun execute(documentParams: DocumentParameters) = flow {
        documentParams.id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(documentParams.user)) fetchData(documentParams)
            else handleUnauthorizedPermission(documentParams.user, Permission.VIEW_LICENSING)

        emit(result)

    }.catch {
        emit(it.handleUnexpectedError())
    }

    override suspend fun execute(queryParams: QueryParameters) = flow {
        val result =
            if (userHasPermission(queryParams.user)) fetchData(queryParams)
            else handleUnauthorizedPermission(queryParams.user, Permission.VIEW_LICENSING)

        emit(result)

    }.catch {
        emit(it.handleUnexpectedError())
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_LICENSING)

    private suspend fun fetchData(params: DocumentParameters): Response<Licensing> =
        when (val response = repository.fetchByDocument(params).single()) {
            is Response.Success -> processResponseSingle(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private suspend fun fetchData(params: QueryParameters): Response<List<Licensing>> =
        when (val response = repository.fetchByQuery(params).single()) {
            is Response.Success -> processResponseList(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private fun processResponseSingle(response: Response.Success<LicensingDto>): Response<Licensing> {
        val entity = processData(response.data)
        return Response.Success(entity)
    }

    private fun processResponseList(response: Response.Success<List<LicensingDto>>): Response<List<Licensing>> {
        val entities = response.data.map { processData(it) }
        return Response.Success(entities)
    }

    private fun processData(dto: LicensingDto): Licensing {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

}