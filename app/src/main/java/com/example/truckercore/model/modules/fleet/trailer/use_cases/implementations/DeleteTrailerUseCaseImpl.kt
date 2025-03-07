package com.example.truckercore.model.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.CheckTrailerExistenceUseCase
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.DeleteTrailerUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class DeleteTrailerUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: TrailerRepository,
    private val checkExistence: CheckTrailerExistenceUseCase
) : UseCase(permissionService), DeleteTrailerUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        checkExistence.execute(user, id).flatMapConcat { response ->
            if (response !is Response.Success) throw ObjectNotFoundException(
                "Attempting to delete a Trailer that was not found for id: $id."
            )
            user.runIfPermitted { repository.delete(id) }
        }

}