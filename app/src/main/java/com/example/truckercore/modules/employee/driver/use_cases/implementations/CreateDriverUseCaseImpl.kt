package com.example.truckercore.modules.employee.driver.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CreateDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreateDriverUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: DriverRepository,
    private val validatorService: ValidatorService,
    private val mapper: DriverMapper
) : UseCase(permissionService), CreateDriverUseCase {

    override fun execute(user: User, driver: Driver): Flow<Response<String>> =
        user.runIfPermitted { processCreation(driver) }

    private fun processCreation(driver: Driver): Flow<Response<String>> {
        validatorService.validateForCreation(driver)
        val dto = mapper.toDto(driver)
        return repository.create(dto)
    }

}