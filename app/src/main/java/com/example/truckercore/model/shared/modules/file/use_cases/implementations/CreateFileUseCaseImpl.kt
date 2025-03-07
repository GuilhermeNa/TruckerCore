package com.example.truckercore.model.shared.modules.file.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.modules.file.entity.File
import com.example.truckercore.model.shared.modules.file.mapper.FileMapper
import com.example.truckercore.model.shared.modules.file.repository.FileRepository
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.CreateFileUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreateFileUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: FileRepository,
    private val validatorService: ValidatorService,
    private val mapper: FileMapper
) : UseCase(permissionService), CreateFileUseCase {

    override fun execute(user: User, file: File): Flow<Response<String>> =
        user.runIfPermitted { processCreation(file) }

    private fun processCreation(file: File): Flow<Response<String>> {
        validatorService.validateForCreation(file)
        val fileDto = mapper.toDto(file)
        return repository.create(fileDto)
    }

}