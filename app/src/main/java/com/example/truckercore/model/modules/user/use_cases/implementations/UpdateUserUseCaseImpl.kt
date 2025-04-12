package com.example.truckercore.model.modules.user.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.modules.user.repository.UserRepository
import com.example.truckercore.model.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.model.modules.user.use_cases.interfaces.UpdateUserUseCase
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateUserUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: UserRepository,
    private val checkExistence: CheckUserExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper
) : UseCase(permissionService), UpdateUserUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, userToUpdate: User): Flow<AppResponse<Unit>> {
        val id =
            userToUpdate.id ?: throw NullPointerException("Null User to updated id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response !is AppResponse.Success) {
                throw ObjectNotFoundException(
                    "Attempting to update a User that was not found for id: $id."
                )
            }
            user.runIfPermitted { processUpdate(userToUpdate) }
        }
    }

    private fun processUpdate(userToUpdate: User): Flow<AppResponse<Unit>> {
        validatorService.validateEntity(userToUpdate)
        val dto = mapper.toDto(userToUpdate)
        return repository.update(dto)
    }

}