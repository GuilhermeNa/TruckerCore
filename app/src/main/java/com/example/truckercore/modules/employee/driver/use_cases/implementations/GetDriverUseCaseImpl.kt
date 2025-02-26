package com.example.truckercore.modules.employee.driver.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetDriverUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: DriverRepository,
    private val validatorService: ValidatorService,
    private val mapper: DriverMapper
) : UseCase(permissionService), GetDriverUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<Driver>> =
        with(documentParams) {
            user.runIfPermitted { getDriverFlow(this) }
        }

    private fun getDriverFlow(documentParams: DocumentParameters): Flow<Response<Driver>> =
        repository.fetchByDocument(documentParams).map { response ->
            if (response is Response.Success) {
                val result = validateAndMapToEntity(response.data)
                Response.Success(result)
            } else Response.Empty
        }

    private fun validateAndMapToEntity(dto: DriverDto): Driver {
        validatorService.validateDto(dto)
        return mapper.toEntity(dto)
    }

}