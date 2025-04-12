package com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces.CheckLicensingExistenceUseCase
import com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces.UpdateLicensingUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateLicensingUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: com.example.truckercore.model.modules.fleet.shared.module.licensing.repository.LicensingRepository,
    private val checkExistence: CheckLicensingExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: LicensingMapper
) : UseCase(permissionService), UpdateLicensingUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, licensing: Licensing): Flow<AppResponse<Unit>> {
        val id = licensing.id ?: throw NullPointerException("Null Licensing id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response !is AppResponse.Success) {
                throw ObjectNotFoundException(
                    "Attempting to update a Licensing that was not found for id: $id."
                )
            }
            user.runIfPermitted { processUpdate(licensing) }
        }
    }

    private fun processUpdate(licensing: Licensing): Flow<AppResponse<Unit>> {
        validatorService.validateEntity(licensing)
        val dto = mapper.toDto(licensing)
        return repository.update(dto)
    }

}