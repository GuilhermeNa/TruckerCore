package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileByIdUseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetStorageFileByIdUseCaseImpl(
    private val repository: StorageFileRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: StorageFileMapper
) : UseCase(), GetStorageFileByIdUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<StorageFile>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) fetchAdminById(id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)

    private suspend fun fetchAdminById(id: String): Response<StorageFile> =
        when (val response = repository.fetchById(id).single()) {
            is Response.Success -> processResponse(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private fun processResponse(response: Response.Success<StorageFileDto>): Response<StorageFile> {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        return Response.Success(entity)
    }

}