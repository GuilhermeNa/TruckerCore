package com.example.truckercore.model.modules.business_central.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.model.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

internal class CreateBusinessCentralUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: BusinessCentralRepository,
    private val validatorService: ValidatorService,
    private val mapper: BusinessCentralMapper,
) : UseCase(permissionService), CreateBusinessCentralUseCase {

    override fun execute(bCentral: BusinessCentral): Flow<AppResponse<String>> {
        validatorService.validateForCreation(bCentral)
        val dto = mapper.toDto(bCentral)
        return repository.create(dto)
    }

}