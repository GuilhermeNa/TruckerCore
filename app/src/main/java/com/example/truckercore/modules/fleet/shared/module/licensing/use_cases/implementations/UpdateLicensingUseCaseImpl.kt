package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CheckLicensingExistenceUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.UpdateLicensingUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateLicensingUseCaseImpl(
    override val requiredPermission: Permission,
    private val repository: LicensingRepository,
    override val permissionService: PermissionService,
    private val checkExistence: CheckLicensingExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: LicensingMapper
) : UseCase(permissionService), UpdateLicensingUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, licensing: Licensing): Flow<Response<Unit>> {
        val id = licensing.id ?: throw NullPointerException("Null Licensing id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response !is Response.Success) {
                throw ObjectNotFoundException(
                    "Attempting to update a Licensing that was not found for id: $id."
                )
            }
            user.runIfPermitted { processUpdate(licensing) }
        }
    }

    private fun processUpdate(licensing: Licensing): Flow<Response<Unit>> {
        validatorService.validateEntity(licensing)
        val dto = mapper.toDto(licensing)
        return repository.update(dto)
    }

}