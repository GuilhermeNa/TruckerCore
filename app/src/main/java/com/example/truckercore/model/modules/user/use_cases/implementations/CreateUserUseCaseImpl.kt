package com.example.truckercore.model.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CreateUserUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreateUserUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: UserRepository,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper,
) : UseCase(permissionService), CreateUserUseCase {

    override fun execute(user: User, newUser: User): Flow<Response<String>> =
        user.runIfPermitted { processCreation(newUser) }

    private fun processCreation(newUser: User): Flow<Response<String>> {
        validatorService.validateForCreation(newUser)
        val dto = mapper.toDto(newUser)
        return repository.create(dto)
    }

}