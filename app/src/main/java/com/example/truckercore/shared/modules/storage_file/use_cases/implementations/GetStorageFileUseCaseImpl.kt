package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetStorageFileUseCaseImpl(
    private val repository: StorageFileRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: StorageFileMapper
) : UseCase(), GetStorageFileUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<StorageFile>> = flow {
        val result =
            if (userHasPermission(user)) fetchData(id)
            else handleUnauthorizedPermission(user, Permission.VIEW_STORAGE_FILE)
        emit(result)
    }.catch {
        emit(handleUnexpectedError(it))
    }

    override suspend fun execute(
        user: User,
        querySettings: List<QuerySettings>
    ): Flow<Response<List<StorageFile>>> = flow {
        val result =
            if (userHasPermission(user)) fetchData(querySettings)
            else handleUnauthorizedPermission(user, Permission.VIEW_STORAGE_FILE)
        emit(result)
    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)

    private suspend fun fetchData(id: String): Response<StorageFile> =
        when (val response = repository.fetchById(id).single()) {
            is Response.Success -> processResponseSingle(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private suspend fun fetchData(settings: List<QuerySettings>): Response<List<StorageFile>> =
        when (val response = repository.fetchByQuery(settings).single()) {
            is Response.Success -> processResponseList(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private fun processResponseSingle(response: Response.Success<StorageFileDto>): Response<StorageFile> {
        val entity = processData(response.data)
        return Response.Success(entity)
    }

    private fun processResponseList(response: Response.Success<List<StorageFileDto>>): Response<List<StorageFile>> {
        val entities = response.data.map { processData(it) }
        return Response.Success(entities)
    }

    private fun processData(dto: StorageFileDto): StorageFile {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

}

