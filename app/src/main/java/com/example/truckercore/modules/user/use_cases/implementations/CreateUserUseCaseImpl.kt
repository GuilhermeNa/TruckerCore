package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CreateUserUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CreateUserUseCaseImpl(
    private val repository: UserRepository,
    private val validatorService: ValidatorService,
    private val permissionService: PermissionService,
    private val mapper: UserMapper
) : CreateUserUseCase {

    override suspend fun execute(user: User, newUser: User): Flow<Response<String>> = flow {

        val result =
            if (userHasPermission(user)) processCreation(newUser)
            else handleUnauthorizedPermission(user)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun processCreation(newUser: User): Response<String> {
        validatorService.validateForCreation(newUser)
        val dto = mapper.toDto(newUser)
        return repository.create(dto).single()
    }

    private fun handleUnauthorizedPermission(user: User): Response.Error {
        val message = "Unauthorized access attempt by user: ${user.id}, for create a new user."
        logWarn("${this.javaClass.simpleName}: $message")
        return Response.Error(exception = UnauthorizedAccessException(message))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.CREATE_USER)

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}