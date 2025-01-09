package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralByIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.services.ResponseHandlerService
import com.example.truckercore.shared.utils.Response
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetBusinessCentralByIdUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val permissionService: PermissionService,
    private val responseHandler: ResponseHandlerService<BusinessCentral, BusinessCentralDto>
): GetBusinessCentralByIdUseCase {

    private companion object {
        private const val UNAUTHORIZED_MESSAGE = "User does not have permission to view the business central."
    }

    override suspend fun execute(user: User, id: String): Flow<Response<BusinessCentral>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result = if (userHasPermission(user)) {
            val response = repository.fetchById(id).single()
            responseHandler.processResponse(response)
        } else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        val result = responseHandler.handleUnexpectedError(it)
        emit(result)
    }

    //----------------------------------------------------------------------------------------------

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_BUSINESS_CENTRAL)

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        logWarn("Unauthorized access attempt by user: ${user.id}, for BusinessCentral ID: $id")
        return Response.Error(exception = UnauthorizedAccessException(UNAUTHORIZED_MESSAGE))
    }

}

/*
internal class GetBusinessCentralByIdUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val validatorService: ValidatorService,
    private val permissionService: PermissionService,
    private val mapper: CentralMapper
) : GetBusinessCentralByIdUseCase {

    private companion object {
        private const val UNAUTHORIZED_MESSAGE =
            "User does not have permission to view the business central."
        private const val EMPTY_MESSAGE = "BusinessCentral with the provided ID not found."
    }

    override suspend fun execute(user: User, id: String): Flow<Response<BusinessCentral>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result = if (userHasPermission(user)) {
            val response = repository.fetchById(id).single()
            processResponse(response)
        } else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        val result = handleUnexpectedError(it)
        emit(result)
    }

    //----------------------------------------------------------------------------------------------

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_BUSINESS_CENTRAL)

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        logWarn("Unauthorized access attempt by user: ${user.id}, for BusinessCentral ID: $id")
        return Response.Error(exception = UnauthorizedAccessException(UNAUTHORIZED_MESSAGE))
    }

    private fun processResponse(response: Response<BusinessCentralDto>): Response<BusinessCentral> {
        return when (response) {
            is Response.Success -> handleSuccessResponse(response)
            is Response.Error -> handleErrorResponse(response)
            is Response.Empty -> handleEmptyResponse(response)
        }
    }

    private fun handleSuccessResponse(response: Response.Success<BusinessCentralDto>): Response<BusinessCentral> {
        return try {
            validatorService.validateDto(response.data)
            val entity = mapper.toEntity(response.data)
            Response.Success(entity)

        } catch (e: MissingFieldException) {
            logError("Error during DTO validation: ${e.message}")
            Response.Error(exception = e)

        } catch (e: InvalidEnumParameterException) {
            logError("Error during DTO validation: ${e.message}")
            Response.Error(exception = e)

        }

    }

    private fun handleErrorResponse(response: Response.Error): Response.Error {
        logError("Error fetching BusinessCentral by ID: ${response.message}")
        return response
    }

    private fun handleEmptyResponse(response: Response.Empty): Response.Empty {
        logWarn(EMPTY_MESSAGE)
        return response
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}*/
