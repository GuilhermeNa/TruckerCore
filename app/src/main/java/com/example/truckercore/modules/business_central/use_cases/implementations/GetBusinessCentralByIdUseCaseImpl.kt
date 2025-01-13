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
) : GetBusinessCentralByIdUseCase {

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

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_BUSINESS_CENTRAL)

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        logWarn("Unauthorized access attempt by user: ${user.id}, for BusinessCentral ID: $id")
        return Response.Error(
            exception = UnauthorizedAccessException(
                "User does not have permission to view the business central."
            )
        )
    }

}