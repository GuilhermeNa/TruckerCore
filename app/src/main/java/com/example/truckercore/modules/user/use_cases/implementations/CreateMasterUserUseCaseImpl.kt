package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CreateMasterUserUseCase
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CreateMasterUserUseCaseImpl(
    private val repository: UserRepository,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper
) : CreateMasterUserUseCase {

    override fun execute(masterUser: User): Flow<Response<String>> {
        validateState(masterUser)
        return processCreation(masterUser)
    }

    private fun validateState(masterUser: User) {
        if (masterUser.level == Level.MASTER) throw InvalidStateException(
            "The first user of the system should have the Master level, but received level: ${masterUser.level}"
        )
    }

    private fun processCreation(masterUser: User): Flow<Response<String>> {
        validatorService.validateForCreation(masterUser)
        val dto = mapper.toDto(masterUser)
        return repository.create(dto)
    }

}