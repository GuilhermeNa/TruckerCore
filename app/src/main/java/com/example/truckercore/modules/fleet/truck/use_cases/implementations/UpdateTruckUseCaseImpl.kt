package com.example.truckercore.modules.fleet.truck.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.UpdateTruckUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdateTruckUseCaseImpl(
    private val repository: TruckRepository,
    private val checkExistence: CheckTruckExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: TruckMapper,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), UpdateTruckUseCase {

    override suspend fun execute(user: User, truck: Truck): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) verifyExistence(user, truck)
            else handleUnauthorizedPermission(user, truck.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun verifyExistence(user: User, truck: Truck): Response<Unit> =
        when (val existenceResponse = checkExistence.execute(user, truck.id!!).single()) {
            is Response.Success -> processUpdate(truck)
            is Response.Empty -> handleNonExistentObject(truck.id)
            is Response.Error -> logAndReturnResponse(existenceResponse)
        }

    private suspend fun processUpdate(truckToUpdate: Truck): Response<Unit> {
        validatorService.validateEntity(truckToUpdate)
        val dto = mapper.toDto(truckToUpdate)
        return repository.update(dto).single()
    }

}