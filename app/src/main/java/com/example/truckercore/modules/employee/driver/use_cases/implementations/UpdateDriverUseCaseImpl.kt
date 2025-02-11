package com.example.truckercore.modules.employee.driver.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CheckDriverExistenceUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.UpdateDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateDriverUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: DriverRepository,
    private val checkExistence: CheckDriverExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: DriverMapper
) : UseCase(permissionService), UpdateDriverUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun execute(user: User, driver: Driver): Flow<Response<Unit>> {
        val id = driver.id ?: throw NullPointerException("Null Driver id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) throw ObjectNotFoundException(
                "Attempting to update a Driver that was not found for id: $id."
            )
            user.runIfPermitted { processUpdate(driver) }
        }
    }

    private fun processUpdate(driverToUpdate: Driver): Flow<Response<Unit>> {
        validatorService.validateEntity(driverToUpdate)
        val dto = mapper.toDto(driverToUpdate)
        return repository.update(dto)
    }

}