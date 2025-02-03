package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingByQuery
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetLicensingByQueryUseCaseImpl(
    private val repository: LicensingRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: LicensingMapper
): UseCase(), GetLicensingByQuery {

    override suspend fun execute(
        user: User,
        querySettings: List<QuerySettings>
    ): Flow<Response<List<Licensing>>> = flow {

        val result =
            if (userHasPermission(user)) fetchData(querySettings)
            else handleUnauthorizedPermission(user, Permission.VIEW_LICENSING)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_LICENSING)

    private suspend fun fetchData(settings: List<QuerySettings>): Response<List<Licensing>> =
        when (val response = repository.testeQuery(settings).single()) {
            is Response.Success -> processResponse(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private fun processResponse(response: Response.Success<List<LicensingDto>>): Response<List<Licensing>> {
        val entities = response.data.map {
            validatorService.validateDto(it)
            mapper.toEntity(it)
        }
        return Response.Success(entities)
    }


}