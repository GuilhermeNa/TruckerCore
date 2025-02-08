package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.NewLicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CreateLicensingUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreateLicensingUseCaseImpl(
    private val repository: LicensingRepository,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission,
    private val validatorService: ValidatorService,
    private val mapper: NewLicensingMapper
) : UseCase(permissionService), CreateLicensingUseCase {

    override suspend fun execute(user: User, licensing: Licensing): Flow<Response<String>> =
        user.runIfPermitted { processCreation(licensing) }

    private fun processCreation(licensing: Licensing): Flow<Response<String>> {
        validatorService.validateForCreation(licensing)
        val dto = mapper.toDto(licensing)
        return repository.create(dto)
    }

}