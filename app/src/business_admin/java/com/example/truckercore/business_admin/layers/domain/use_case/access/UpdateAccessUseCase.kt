package com.example.truckercore.business_admin.layers.domain.use_case.access

import android.util.Log
import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.getOrThrow
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.infra.security.PermissionService
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data_2.cache.SessionCache
import com.example.truckercore.layers.data_2.repository.interfaces.AccessRepository
import com.example.truckercore.layers.data_2.repository.interfaces.UserRepository
import com.example.truckercore.layers.domain.base.contracts.EmployeeID
import com.example.truckercore.layers.domain.model.access.Access
import com.example.truckercore.layers.domain.model.access.Role

class UpdateAccessUseCase(
    private val userRepository: UserRepository,
    private val accessRepository: AccessRepository
) {

    suspend operator fun invoke(
        employeeID: EmployeeID, role: Role
    ): OperationOutcome {
        return try {

            // Early return if user
            // has no permission
            if (!hasPermission()) {
                return unauthorizedOutcome()
            }

            // Fetch Access, update and save
            val newAccess = fetchAndUpdateRole(employeeID, role)
            accessRepository.save(newAccess)

            OperationOutcome.Completed

        } catch (e: Exception) {
            failure(e)
        }
    }

    private suspend fun hasPermission(): Boolean {
        val user = userRepository.completeFetch(SessionCache.userID).getOrThrow()
        return PermissionService.canUpdateAccess(user)
    }

    private suspend fun fetchAndUpdateRole(id: EmployeeID, role: Role): Access {
        val user = userRepository.completeFetch(id).getOrThrow()
        val newUser = user.update(role)
        return newUser.access
    }

    private fun unauthorizedOutcome() = OperationOutcome.Failure(
        DomainException.UnauthorizedAccess(
            "User must be an Admin to change Access. Role found: ${SessionCache.role}"
        )
    )

    private fun failure(throwable: Throwable): OperationOutcome.Failure {
        Log.e(getTag, "An error occurred while updating an Employee Access", throwable)

        // Return when its an known AppException
        if (throwable is AppException) {
            return OperationOutcome.Failure(throwable)
        }

        // Build an AppException if error is unknown
        val exception = AppException(throwable.message, throwable)
        return OperationOutcome.Failure(exception)
    }

}