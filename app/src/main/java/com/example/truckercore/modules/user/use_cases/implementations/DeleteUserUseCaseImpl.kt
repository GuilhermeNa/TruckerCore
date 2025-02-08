package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.DeleteUserUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class DeleteUserUseCaseImpl(
    private val repository: UserRepository,
    private val checkExistence: CheckUserExistenceUseCase,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), DeleteUserUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) continueForExistenceCheckage(user, id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun continueForExistenceCheckage(user: User, id: String) =
        when (
            val existenceResponse = checkExistence.execute(user, id).single()
        ) {
            is Response.Success -> delete(id)
            is Response.Empty -> handleNonExistentObject(id)
            is Response.Error -> logAndReturnResponse(existenceResponse)
        }

    private suspend fun delete(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

}