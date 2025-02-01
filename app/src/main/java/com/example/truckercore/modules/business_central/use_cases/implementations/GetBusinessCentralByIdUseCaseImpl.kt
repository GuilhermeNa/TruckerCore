package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralByIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class GetBusinessCentralByIdUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: BusinessCentralMapper
) : GetBusinessCentralByIdUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<BusinessCentral>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result = if (userHasPermission(user)) {
            val response = repository.fetchById(id).single()
            processResponse(response)
        } else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_BUSINESS_CENTRAL)

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        logWarn("${javaClass.simpleName}: Unauthorized access attempt by user: ${user.id}, for BusinessCentral ID: $id")
        return Response.Error(
            UnauthorizedAccessException(
                "User does not have permission to view the business central."
            )
        )
    }

    private fun processResponse(response: Response<BusinessCentralDto>): Response<BusinessCentral> {
        return when (response) {
            is Response.Success -> handleSuccessResponse(response)
            is Response.Error -> handleFailureResponse(response)
            is Response.Empty -> handleEmptyResponse(response)
        }
    }

    private fun handleSuccessResponse(response: Response.Success<BusinessCentralDto>): Response<BusinessCentral> {
        validatorService.validateDto(response.data)
        val entity = mapper.toEntity(response.data)
        return Response.Success(entity)
    }

    private fun handleFailureResponse(response: Response.Error): Response.Error {
        logError("${javaClass.simpleName}: Received an error from database.")
        return response
    }

    private fun handleEmptyResponse(response: Response.Empty): Response.Empty {
        logWarn("${javaClass.simpleName}: The database response came back empty.")
        return response
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${javaClass.simpleName}: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}