package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CheckStorageFileExistenceUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.UpdateStorageFileUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdateStorageFileUseCaseImpl(
    private val repository: StorageFileRepository,
    private val checkExistence: CheckStorageFileExistenceUseCase,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: StorageFileMapper
) : UseCase(), UpdateStorageFileUseCase {

    override suspend fun execute(user: User, file: StorageFile): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) verifyExistence(user, file)
            else handleUnauthorizedPermission(user, file.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.UPDATE_STORAGE_FILE)

    private suspend fun verifyExistence(user: User, file: StorageFile): Response<Unit> =
        when (val existenceResponse = checkExistence.execute(user, file.id!!).single()) {
            is Response.Success -> processUpdate(file)
            is Response.Empty -> handleNonExistentObject(file.id)
            is Response.Error -> handleFailureResponse(existenceResponse)
        }

    private suspend fun processUpdate(file: StorageFile): Response<Unit> {
        validatorService.validateEntity(file)
        val dto = mapper.toDto(file)
        return repository.update(dto).single()
    }

}
