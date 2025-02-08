package com.example.truckercore.modules.fleet.trailer.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.CreateTrailerUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateTrailerUseCaseImpl(
    private val repository: TrailerRepository,
    private val validatorService: ValidatorService,
    override val permissionService: PermissionService,
    private val mapper: TrailerMapper,
    override val requiredPermission: Permission
) : UseCase(permissionService), CreateTrailerUseCase {

    override suspend fun execute(user: User, trailer: Trailer): Flow<Response<String>> = flow {
        val result =
            if (userHasPermission(user)) processCreation(trailer)
            else handleUnauthorizedPermission(user, trailer.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun processCreation(trailer: Trailer): Response<String> {
        validatorService.validateForCreation(trailer)
        val dto = mapper.toDto(trailer)
        return repository.create(dto).single()
    }

}