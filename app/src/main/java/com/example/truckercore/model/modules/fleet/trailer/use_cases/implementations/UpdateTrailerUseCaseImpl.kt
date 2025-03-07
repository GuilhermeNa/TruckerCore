package com.example.truckercore.model.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.model.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.CheckTrailerExistenceUseCase
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.UpdateTrailerUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateTrailerUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: TrailerRepository,
    private val checkExistence: CheckTrailerExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: TrailerMapper
) : UseCase(permissionService), UpdateTrailerUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, trailer: Trailer): Flow<Response<Unit>> {
        val id = trailer.id ?: throw NullPointerException("Null Trailer id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response !is Response.Success) {
                throw ObjectNotFoundException(
                    "Attempting to update a Trailer that was not found for id: $id."
                )
            }
            user.runIfPermitted { processUpdate(trailer) }
        }
    }

    private fun processUpdate(trailer: Trailer): Flow<Response<Unit>> {
        validatorService.validateEntity(trailer)
        val dto = mapper.toDto(trailer)
        return repository.update(dto)
    }

}