package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CreateStorageFileUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateStorageFileUseCaseImpl(
    private val repository: StorageFileRepository,
    private val validatorService: ValidatorService,
    private val permissionService: PermissionService,
    private val mapper: StorageFileMapper
) : UseCase(), CreateStorageFileUseCase {

    override suspend fun execute(user: User, file: StorageFile): Flow<Response<String>> =
        flow {
            val result =
                if (userHasPermission(user)) processCreation(file)
                else handleUnauthorizedPermission(user, file.id!!)

            emit(result)

        }.catch {
            emit(handleUnexpectedError(it))
        }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.CREATE_STORAGE_FILE)

    private suspend fun processCreation(file: StorageFile): Response<String> {
        validatorService.validateForCreation(file)
        val fileDto = mapper.toDto(file)
        return repository.create(fileDto).single()
    }

}