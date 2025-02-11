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

internal class CreateStorageFileUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: StorageFileRepository,
    private val validatorService: ValidatorService,
    private val mapper: StorageFileMapper
) : UseCase(permissionService), CreateStorageFileUseCase {

    override fun execute(user: User, file: StorageFile): Flow<Response<String>> =
        user.runIfPermitted { processCreation(file) }

    private fun processCreation(file: StorageFile): Flow<Response<String>> {
        validatorService.validateForCreation(file)
        val fileDto = mapper.toDto(file)
        return repository.create(fileDto)
    }

}