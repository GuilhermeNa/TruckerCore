package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logAndReturnEmpty
import com.example.truckercore.shared.utils.expressions.logAndReturnError
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetStorageFileUseCaseImpl(
    private val repository: StorageFileRepository,
    override val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: StorageFileMapper
) : UseCase(permissionService), GetStorageFileUseCase {

    override val requiredPermission: Permission = Permission.VIEW_STORAGE_FILE

    override fun execute(documentParams: DocumentParameters): Flow<Response<StorageFile>> =
        fetchData(
            documentParams = documentParams,
            operation = this::getMappedFileFlow
        )

    private fun getMappedFileFlow(documentParams: DocumentParameters) =
        repository.fetchByDocument(documentParams).map { response ->
            when (response) {
                is Response.Success -> response.processFiles()
                is Response.Empty -> response.logAndReturnEmpty()
                is Response.Error -> response.logAndReturnError()
            }
        }

    private fun Response.Success<StorageFileDto>.processFiles(): Response<StorageFile> {
        val entity = validateAndMapToEntity(this.data)
        return Response.Success(entity)
    }

    private fun validateAndMapToEntity(dto: StorageFileDto): StorageFile {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<StorageFile>>> =
        fetchData(
            queryParams = queryParams,
            operation = this::getMappedFilesListFLow
        )

    private fun getMappedFilesListFLow(queryParams: QueryParameters) =
        repository.fetchByQuery(queryParams).map { response ->
            when (response) {
                is Response.Success -> response.processFilesList()
                is Response.Empty -> response.logAndReturnEmpty()
                is Response.Error -> response.logAndReturnError()
            }
        }

    private fun Response.Success<List<StorageFileDto>>.processFilesList(): Response<List<StorageFile>> {
        val entities = this.data.map { validateAndMapToEntity(it) }
        return Response.Success(entities)
    }

}

