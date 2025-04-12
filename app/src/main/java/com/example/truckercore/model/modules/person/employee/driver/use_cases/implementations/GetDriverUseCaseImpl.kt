package com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.model.modules.person.employee.driver.entity.Driver
import com.example.truckercore.model.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.person.employee.driver.repository.DriverRepository
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetDriverUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: DriverRepository,
    private val validatorService: ValidatorService,
    private val mapper: DriverMapper
) : UseCase(permissionService), GetDriverUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<AppResponse<Driver>> =
        with(documentParams) {
            user.runIfPermitted { getDriverFlow(this) }
        }

    private fun getDriverFlow(documentParams: DocumentParameters): Flow<AppResponse<Driver>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is AppResponse.Success) {
                val result = validateAndMapToEntity(response.data)
                AppResponse.Success(result)
            } else AppResponse.Empty
        }

    private fun validateAndMapToEntity(dto: DriverDto): Driver {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<AppResponse<List<Driver>>> =
        with(queryParams) {
            user.runIfPermitted { getDriverListFlow(this) }
        }

    private fun getDriverListFlow(queryParams: QueryParameters): Flow<AppResponse<List<Driver>>> =
        repository.fetchByQuery(queryParams).map { response ->
            if (response is AppResponse.Success) {
                val result = response.data.map { validateAndMapToEntity(it) }
                AppResponse.Success(result)
            } else AppResponse.Empty
        }

}