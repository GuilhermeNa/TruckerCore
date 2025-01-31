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
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdateDriverUseCaseImpl(
    private val repository: DriverRepository,
    private val checkExistence: CheckDriverExistenceUseCase,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: DriverMapper
) : UseCase(), UpdateDriverUseCase {

    override suspend fun execute(user: User, driver: Driver): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) continueForExistenceCheckage(user, driver)
            else handleUnauthorizedPermission(user, driver.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.UPDATE_DRIVER)

    private suspend fun continueForExistenceCheckage(user: User, driver: Driver) =
        when (val existenceResponse = checkExistence.execute(user, driver.id!!).single()) {
            is Response.Success -> processUpdate(driver)
            is Response.Empty -> handleNonExistentObject(driver.id)
            is Response.Error -> handleFailureResponse(existenceResponse)
        }

    private suspend fun processUpdate(driverToUpdate: Driver): Response<Unit> {
        validatorService.validateEntity(driverToUpdate)
        val dto = mapper.toDto(driverToUpdate)
        return repository.update(dto).single()
    }

}