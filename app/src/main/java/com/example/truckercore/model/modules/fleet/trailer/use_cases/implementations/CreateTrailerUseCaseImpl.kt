package com.example.truckercore.model.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.model.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.CreateTrailerUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

internal class CreateTrailerUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: TrailerRepository,
    private val validatorService: ValidatorService,
    private val mapper: TrailerMapper
) : UseCase(permissionService), CreateTrailerUseCase {

    override fun execute(user: User, trailer: Trailer): Flow<AppResponse<String>> =
        user.runIfPermitted { processCreation(trailer) }

    private fun processCreation(trailer: Trailer): Flow<AppResponse<String>> {
        validatorService.validateForCreation(trailer)
        val dto = mapper.toDto(trailer)
        return repository.create(dto)
    }

}