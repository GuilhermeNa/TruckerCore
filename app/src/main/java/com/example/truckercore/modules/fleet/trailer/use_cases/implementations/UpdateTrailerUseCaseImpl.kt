package com.example.truckercore.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.CheckTrailerExistenceUseCase
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.UpdateTrailerUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdateTrailerUseCaseImpl(
    private val repository: TrailerRepository,
    private val checkExistence: CheckTrailerExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: TrailerMapper,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), UpdateTrailerUseCase {

    override suspend fun execute(user: User, trailer: Trailer): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) verifyExistence(user, trailer)
            else handleUnauthorizedPermission(user, trailer.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun verifyExistence(user: User, trailer: Trailer): Response<Unit> =
        when (val existenceResponse = checkExistence.execute(user, trailer.id!!).single()) {
            is Response.Success -> processUpdate(trailer)
            is Response.Empty -> handleNonExistentObject(trailer.id)
            is Response.Error -> logAndReturnResponse(existenceResponse)
        }

    private suspend fun processUpdate(trailerToUpdate: Trailer): Response<Unit> {
        validatorService.validateEntity(trailerToUpdate)
        val dto = mapper.toDto(trailerToUpdate)
        return repository.update(dto).single()
    }

}