package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.modules.business_central.validator.CentralCreationValidator
import com.example.truckercore.modules.user.entity.User

internal class CreateBusinessCentralUseCaseImpl(
    override val repository: BusinessCentralRepository,
    override val service: PermissionService
): CreateBusinessCentralUseCase {

    override fun execute(dto: BusinessCentralDto): String {
        CentralCreationValidator.execute(dto)
        return repository.create(dto)
    }

    override fun userHasPermission(user: User): Boolean {
        TODO("Not yet implemented")
    }

}