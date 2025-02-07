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
import com.example.truckercore.shared.utils.expressions.handleUnexpectedError
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
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

    override suspend fun execute(documentParam: DocumentParameters): Flow<Response<StorageFile>> =
        flow {
            val result =
                if (userHasPermission(documentParam.user)) fetchData(documentParam)
                else handleUnauthorizedPermission(documentParam.user, Permission.VIEW_STORAGE_FILE)
            emit(result)
        }.catch {
            emit(handleUnexpectedError(it))
        }

    override suspend fun execute(queryParam: QueryParameters): Flow<Response<List<StorageFile>>> =
        flow {
            val result =
                if (userHasPermission(queryParam.user)) fetchData(queryParam)
                else handleUnauthorizedPermission(queryParam.user, Permission.VIEW_STORAGE_FILE)
            emit(result)
        }.catch {
            emit(it.handleUnexpectedError())
        }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)

    private suspend fun fetchData(documentParam: DocumentParameters): Response<StorageFile> =
        when (val response = repository.fetchByDocument(documentParam).single()) {
            is Response.Success -> processResponseSingle(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private suspend fun fetchData(queryParam: QueryParameters): Response<List<StorageFile>> =
        when (val response = repository.fetchByQuery(queryParam).single()) {
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

