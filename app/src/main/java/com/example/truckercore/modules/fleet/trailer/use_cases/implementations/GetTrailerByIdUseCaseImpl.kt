package com.example.truckercore.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.GetTrailerByIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.sealeds.Response.Empty
import com.example.truckercore.shared.sealeds.Response.Error
import com.example.truckercore.shared.sealeds.Response.Success
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetTrailerByIdUseCaseImpl(
    private val repository: TrailerRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: TrailerMapper
) : UseCase(), GetTrailerByIdUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Trailer>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) fetchTrailerById(id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_TRAILER)

    private suspend fun fetchTrailerById(id: String): Response<Trailer> =
        when (val response = repository.fetchById(id).single()) {
            is Success -> processResponse(response)
            is Error -> handleFailureResponse(response)
            is Empty -> response
        }

    private fun processResponse(response: Success<TrailerDto>): Response<Trailer> {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        return Success(entity)
    }

}