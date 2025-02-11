package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreateBusinessCentralUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: BusinessCentralRepository,
    private val validatorService: ValidatorService,
    private val mapper: BusinessCentralMapper,
) : UseCase(permissionService), CreateBusinessCentralUseCase {

    override suspend fun execute(user: User, bCentral: BusinessCentral): Flow<Response<String>> {
        validateUser(user)
        return user.runIfPermitted { processCreation(bCentral) }
    }

    private fun validateUser(user: User) {
        if (user.level != Level.MASTER) throw InvalidStateException(
            "The user should have Master leve for create a new Business Central."
        )
    }

    private fun processCreation(bCentral: BusinessCentral): Flow<Response<String>> {
        validatorService.validateForCreation(bCentral)
        val dto = mapper.toDto(bCentral)
        return repository.create(dto)
    }

}