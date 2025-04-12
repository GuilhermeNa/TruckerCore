package com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.model.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataUseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetPersonalDataUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: PersonalDataRepository,
    private val validatorService: ValidatorService,
    private val mapper: PersonalDataMapper
) : UseCase(permissionService), GetPersonalDataUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<PersonalData>> =
        with(documentParams) {
            user.runIfPermitted { getMappedPersonalDataFlow(this) }
        }

    private fun getMappedPersonalDataFlow(
        documentParams: DocumentParameters
    ): Flow<Response<PersonalData>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                Response.Success(validateAndMapToEntity(response.data))
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: PersonalDataDto): PersonalData {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<PersonalData>>> =
        with(queryParams) {
            user.runIfPermitted { getMappedPersonalDataListFLow(queryParams) }
        }

    private fun getMappedPersonalDataListFLow(
        queryParams: QueryParameters
    ): Flow<Response<List<PersonalData>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is Response.Success) {
                Response.Success(response.data.map { validateAndMapToEntity(it) })
            } else Response.Empty
        }

}