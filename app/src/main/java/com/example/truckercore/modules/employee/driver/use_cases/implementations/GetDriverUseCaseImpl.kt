package com.example.truckercore.modules.employee.driver.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetDriverUseCaseImpl(
    private val repository: DriverRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: DriverMapper
) : UseCase(), GetDriverUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Driver>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) fetchDriverById(id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_DRIVER)

    private suspend fun fetchDriverById(id: String): Response<Driver> =
        when (val response = repository.fetchById(id).single()) {
            is Response.Success -> processResponse(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> response
        }

    private fun processResponse(response: Response.Success<DriverDto>): Response<Driver> {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        return Response.Success(entity)
    }

}