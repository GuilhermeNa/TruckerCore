package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CreateLicensingUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.handleUnexpectedError
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateLicensingUseCaseImpl(
    private val repository: LicensingRepository,
    private val validatorService: ValidatorService,
    private val permissionService: PermissionService,
    private val mapper: LicensingMapper
) : UseCase(), CreateLicensingUseCase {

    override suspend fun execute(user: User, licensing: Licensing): Flow<Response<String>> = flow {
        val result =
            if (userHasPermission(user)) processCreation(licensing)
            else handleUnauthorizedPermission(user, licensing.id!!)

        emit(result)

    }.catch {
        emit(it.handleUnexpectedError())
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.CREATE_LICENSING)

    private suspend fun processCreation(licensing: Licensing): Response<String> {
        validatorService.validateForCreation(licensing)
        val dto = mapper.toDto(licensing)
        return repository.create(dto).single()
    }

}