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
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileByParentIdUseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetStorageFileByParentIdUseCaseImpl(
    private val repository: StorageFileRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: StorageFileMapper
) : UseCase(), GetStorageFileByParentIdUseCase {

    override suspend fun execute(
        user: User,
        parentId: String
    ): Flow<Response<List<StorageFile>>> = flow {
        parentId.validateIsNotBlank(Field.PARENT_ID.getName())

        val result =
            if (userHasPermission(user)) fetchByParentId(parentId)
            else handleUnauthorizedPermission(user, parentId)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)

    private suspend fun fetchByParentId(parentId: String): Response<List<StorageFile>> =
        when (val response = repository.fetchByParentId(parentId).single()) {
            is Response.Success -> processData(response.data)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private fun processData(dtos: List<StorageFileDto>): Response<List<StorageFile>> {
        dtos.forEach { validatorService.validateDto(it) }
        val data = dtos.map { mapper.toEntity(it) }
        return Response.Success(data)
    }

}