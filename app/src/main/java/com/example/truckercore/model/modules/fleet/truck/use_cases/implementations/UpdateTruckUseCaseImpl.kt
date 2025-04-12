package com.example.truckercore.model.modules.fleet.truck.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.model.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.UpdateTruckUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateTruckUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: TruckRepository,
    private val checkExistence: CheckTruckExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: TruckMapper
) : UseCase(permissionService), UpdateTruckUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, truck: Truck): Flow<Response<Unit>> {
        val id = truck.id ?: throw NullPointerException("Null Truck id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response !is Response.Success) {
                throw ObjectNotFoundException(
                    "Attempting to update a Truck that was not found for id: $id."
                )
            }
            user.runIfPermitted { processUpdate(truck) }
        }
    }

    private fun processUpdate(truckToUpdate: Truck): Flow<Response<Unit>> {
        validatorService.validateEntity(truckToUpdate)
        val dto = mapper.toDto(truckToUpdate)
        return repository.update(dto)
    }

}