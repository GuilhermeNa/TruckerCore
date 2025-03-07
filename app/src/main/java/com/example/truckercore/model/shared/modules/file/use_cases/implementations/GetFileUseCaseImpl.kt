package com.example.truckercore.model.shared.modules.file.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.modules.file.dto.FileDto
import com.example.truckercore.model.shared.modules.file.entity.File
import com.example.truckercore.model.shared.modules.file.mapper.FileMapper
import com.example.truckercore.model.shared.modules.file.repository.FileRepository
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetFileUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: FileRepository,
    private val validatorService: ValidatorService,
    private val mapper: FileMapper
) : UseCase(permissionService), GetFileUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<File>> =
        with(documentParams) {
            user.runIfPermitted { getMappedFileFlow(this) }
        }

    private fun getMappedFileFlow(documentParams: DocumentParameters): Flow<Response<File>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                Response.Success(validateAndMapToEntity(response.data))
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: FileDto): File {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<File>>> =
        with(queryParams) {
            user.runIfPermitted { getMappedFilesListFLow(queryParams) }
        }

    private fun getMappedFilesListFLow(queryParams: QueryParameters): Flow<Response<List<File>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                Response.Success(response.data.map { validateAndMapToEntity(it) })
            } else Response.Empty
        }

}

