package com.example.truckercore.shared.modules.file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.mapper.FileMapper
import com.example.truckercore.shared.modules.file.repository.FileRepository
import com.example.truckercore.shared.modules.file.use_cases.interfaces.CheckFileExistenceUseCase
import com.example.truckercore.shared.modules.file.use_cases.interfaces.UpdateFileUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateFileUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: FileRepository,
    private val checkExistence: CheckFileExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: FileMapper
) : UseCase(permissionService), UpdateFileUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun execute(user: User, file: File): Flow<Response<Unit>> {
        val id = file.id ?: throw NullPointerException("Null File id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) throw ObjectNotFoundException(
                "Attempting to update a File that was not found for id: $id."
            )
            user.runIfPermitted { processUpdate(file) }
        }
    }

    private fun processUpdate(file: File): Flow<Response<Unit>> {
        validatorService.validateEntity(file)
        val dto = mapper.toDto(file)
        return repository.update(dto)
    }

}
