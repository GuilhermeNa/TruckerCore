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
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdateLicensingUseCaseImpl(
    private val repository: LicensingRepository,
    private val checkExistence: CheckLicensingExistenceUseCase,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: LicensingMapper
) : UseCase(), UpdateLicensingUseCase {

    override suspend fun execute(user: User, licensing: Licensing): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) verifyExistence(user, licensing)
            else handleUnauthorizedPermission(user, licensing.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)

    private suspend fun verifyExistence(user: User, licensing: Licensing): Response<Unit> =
        when (val existenceResponse = checkExistence.execute(user, licensing.id!!).single()) {
            is Response.Success -> processUpdate(licensing)
            is Response.Empty -> handleNonExistentObject(licensing.id)
            is Response.Error -> handleFailureResponse(existenceResponse)
        }

    private suspend fun processUpdate(licensingToUpdate: Licensing): Response<Unit> {
        validatorService.validateEntity(licensingToUpdate)
        val dto = mapper.toDto(licensingToUpdate)
        return repository.update(dto).single()
    }

}