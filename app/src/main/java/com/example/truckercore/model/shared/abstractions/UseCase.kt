/*
package com.example.truckercore.model.shared.abstractions

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.data.User
import kotlinx.coroutines.flow.Flow

*/
/**
 * Abstract class representing a base use case.
 *
 * This class provides common functionality for use cases, such as handling errors, permission checks,
 * and managing the flow of execution.
 *
 * @see Response
 * @see User
 *//*

internal abstract class UseCase(
    open val permissionService: PermissionService
) {

    */
/**
     * The permission required to perform the action associated with this use case.
     * This property must be defined in subclasses that inherit from [UseCase].
     *//*

    protected abstract val requiredPermission: Permission

    */
/**
     * Checks if the given user has the required permission to execute the use case.
     *
     * @param user The user whose permissions will be checked.
     * @return True if the user has the required permission, false otherwise.
     *//*

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, requiredPermission)

    */
/**
     * Executes the provided block of code if the current user has the required permission to perform the action.
     *
     * If the user does not have the required permission, an [UnauthorizedAccessException] will be thrown.
     *
     * @param block The block of code to be executed if the user has the required permission.
     * @return A [Flow] containing the response from executing the block of code.
     * @throws UnauthorizedAccessException If the user does not have the necessary permission.
     *//*

    fun <T> User.runIfPermitted(block: () -> Flow<Response<T>>): Flow<Response<T>> {
        return if (userHasPermission(this)) block()
        else throw UnauthorizedAccessException(this, requiredPermission)
    }

}*/
