package com.example.truckercore.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.CheckTrailerExistenceUseCase
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.DeleteTrailerUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class DeleteTrailerUseCaseImpl(
    private val repository: TrailerRepository,
    private val checkExistence: CheckTrailerExistenceUseCase,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), DeleteTrailerUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) verifyExistence(user, id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun verifyExistence(user: User, id: String) =
        when (val existenceResponse = checkExistence.execute(user, id).single()) {
            is Response.Success -> delete(id)
            is Response.Empty -> handleNonExistentObject(id)
            is Response.Error -> logAndReturnResponse(existenceResponse)
        }

    private suspend fun delete(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

}