package com.example.truckercore.modules.fleet.truck.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.CreateTruckUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateTruckUseCaseImpl(
    private val repository: TruckRepository,
    private val validatorService: ValidatorService,
    private val permissionService: PermissionService,
    private val mapper: TruckMapper
) : UseCase(), CreateTruckUseCase {

    override suspend fun execute(user: User, truck: Truck): Flow<Response<String>> = flow {
        val result =
            if (userHasPermission(user)) processCreation(truck)
            else handleUnauthorizedPermission(user, truck.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.CREATE_TRUCK)

    private suspend fun processCreation(truck: Truck): Response<String> {
        validatorService.validateForCreation(truck)
        val dto = mapper.toDto(truck)
        return repository.create(dto).single()
    }

}