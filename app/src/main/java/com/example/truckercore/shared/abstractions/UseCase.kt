package com.example.truckercore.shared.abstractions

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Abstract class representing a base use case.
 *
 * This class provides common functionality for use cases, such as handling errors, permission checks,
 * and managing the flow of execution.
 *
 * @see Response
 * @see User
 */
internal abstract class UseCase(
    open val permissionService: PermissionService,
) {

    protected abstract val requiredPermission: Permission

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, requiredPermission)

    fun <T> User.runIfPermitted(block: () -> Flow<Response<T>>): Flow<Response<T>> {
        return if (userHasPermission(this)) block()
        else throw UnauthorizedAccessException(this, requiredPermission)
    }

}