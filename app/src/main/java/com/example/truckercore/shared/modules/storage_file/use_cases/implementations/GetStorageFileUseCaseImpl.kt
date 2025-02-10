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
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetStorageFileUseCaseImpl(
    private val repository: StorageFileRepository,
    override val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: StorageFileMapper,
    override val requiredPermission: Permission
) : UseCase(permissionService), GetStorageFileUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<StorageFile>> =
        with(documentParams) {
            user.runIfPermitted { getMappedFileFlow(this) }
        }

    private fun getMappedFileFlow(documentParams: DocumentParameters): Flow<Response<StorageFile>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                Response.Success(validateAndMapToEntity(response.data))
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: StorageFileDto): StorageFile {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<StorageFile>>> =
        with(queryParams) {
            user.runIfPermitted { getMappedFilesListFLow(queryParams) }
        }

    private fun getMappedFilesListFLow(queryParams: QueryParameters): Flow<Response<List<StorageFile>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                Response.Success(response.data.map { validateAndMapToEntity(it) })
            } else Response.Empty
        }

}

