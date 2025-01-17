package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CreateUserUseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateUserUseCaseImpl(
    private val repository: UserRepository,
    private val validatorService: ValidatorService,
    private val mapper: UserMapper
): CreateUserUseCase {

    override suspend fun execute(user: User, newUser: User): Flow<Response<String>> = flow {
        validatorService.validateForCreation(newUser)
        val dto = mapper.toDto(newUser)
        emit(repository.create(dto).single())
    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}