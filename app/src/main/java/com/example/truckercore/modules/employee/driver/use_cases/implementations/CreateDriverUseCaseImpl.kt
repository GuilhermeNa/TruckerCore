package com.example.truckercore.modules.employee.driver.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CreateDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateDriverUseCaseImpl(
    private val repository: DriverRepository,
    private val validatorService: ValidatorService,
    private val permissionService: PermissionService,
    private val mapper: DriverMapper
) : UseCase(), CreateDriverUseCase {

    override suspend fun execute(user: User, driver: Driver): Flow<Response<String>> = flow {
        val result =
            if (userHasPermission(user)) processCreation(driver)
            else handleUnauthorizedPermission(user, driver.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.CREATE_DRIVER)

    private suspend fun processCreation(driver: Driver): Response<String> {
        validatorService.validateForCreation(driver)
        val dto = mapper.toDto(driver)
        return repository.create(dto).single()
    }

}