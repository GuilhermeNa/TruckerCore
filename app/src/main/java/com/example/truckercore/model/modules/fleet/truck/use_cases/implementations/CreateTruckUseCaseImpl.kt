package com.example.truckercore.model.modules.fleet.truck.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.model.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.CreateTruckUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreateTruckUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: TruckRepository,
    private val validatorService: ValidatorService,
    private val mapper: TruckMapper
) : UseCase(permissionService), CreateTruckUseCase {

    override fun execute(user: User, truck: Truck): Flow<Response<String>> =
        user.runIfPermitted { processCreation(truck) }

    private fun processCreation(truck: Truck): Flow<Response<String>> {
        validatorService.validateForCreation(truck)
        val dto = mapper.toDto(truck)
        return repository.create(dto)
    }

}