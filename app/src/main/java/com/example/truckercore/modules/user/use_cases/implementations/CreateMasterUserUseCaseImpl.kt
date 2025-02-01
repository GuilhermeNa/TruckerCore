package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CreateMasterUserUseCase
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateMasterUserUseCaseImpl(
    private val repository: UserRepository,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper
) : CreateMasterUserUseCase {

    override suspend fun execute(masterUser: User): Flow<Response<String>> = flow {
        val response =
            if (isUserInCorrectLevel(masterUser)) processCreation(masterUser)
            else handleWrongLevel(masterUser.level)

        emit(response)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun processCreation(masterUser: User): Response<String> {
        validatorService.validateForCreation(masterUser)
        val dto = mapper.toDto(masterUser)
        return repository.create(dto).single()
    }

    private fun handleWrongLevel(receivedLevel: Level) = Response.Error(
        InvalidStateException("MasterUser level should be ${Level.MASTER.name} and received: $receivedLevel")
    )

    private fun isUserInCorrectLevel(masterUser: User): Boolean =
        masterUser.level == Level.MASTER

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}