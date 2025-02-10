package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.DeleteUserUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class DeleteUserUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: UserRepository,
    private val checkExistence: CheckUserExistenceUseCase
) : UseCase(permissionService), DeleteUserUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        checkExistence.execute(user, id).flatMapConcat { response ->
            if (response !is Response.Success) {
                throw ObjectNotFoundException(
                    "Attempting to delete an User that was not found for id: $id."
                )
            }
            user.runIfPermitted { repository.delete(id) }
        }

}