package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CheckStorageFileExistenceUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.DeleteStorageFileUseCase
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class DeleteStorageFileUseCaseImpl(
    private val repository: StorageFileRepository,
    private val checkExistence: CheckStorageFileExistenceUseCase,
    private val permissionService: PermissionService
) : UseCase(), DeleteStorageFileUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) verifyExistence(user, id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.DELETE_STORAGE_FILE)

    private suspend fun verifyExistence(user: User, id: String): Response<Unit> =
        when (val existenceResponse = checkExistence.execute(user, id).single()) {
            is Response.Success -> deleteFile(id)
            is Response.Empty -> handleNonExistentObject(id)
            is Response.Error -> handleFailureResponse(existenceResponse)
        }

    private suspend fun deleteFile(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

}