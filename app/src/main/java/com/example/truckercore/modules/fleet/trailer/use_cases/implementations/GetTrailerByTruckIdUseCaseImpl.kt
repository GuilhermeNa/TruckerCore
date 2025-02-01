package com.example.truckercore.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.GetTrailerByTruckIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.utils.sealeds.Response.Empty
import com.example.truckercore.shared.utils.sealeds.Response.Error
import com.example.truckercore.shared.utils.sealeds.Response.Success
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetTrailerByTruckIdUseCaseImpl(
    private val repository: TrailerRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: TrailerMapper
) : UseCase(), GetTrailerByTruckIdUseCase {

    override suspend fun execute(user: User, truckId: String): Flow<Response<List<Trailer>>> =
        flow {
            truckId.validateIsNotBlank(Field.ID.name)

            val result =
                if (userHasPermission(user)) fetchByTruckId(truckId)
                else handleUnauthorizedPermission(user, Permission.VIEW_TRAILER)

            emit(result)

        }.catch {
            emit(handleUnexpectedError(it))
        }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_TRAILER)

    private suspend fun fetchByTruckId(truckId: String): Response<List<Trailer>> =
        when (val response = repository.fetchByTruckId(truckId).single()) {
            is Success -> processResponse(response)
            is Error -> handleFailureResponse(response)
            is Empty -> response
        }

    private fun processResponse(response: Success<List<TrailerDto>>): Response<List<Trailer>> {
        val entities = response.data.map {
            validatorService.validateDto(it)
            mapper.toEntity(it)
        }
        return Success(entities)
    }

}