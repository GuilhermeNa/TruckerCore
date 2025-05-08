package com.example.truckercore.model.infrastructure.security.errors

import com.example.truckercore.model.infrastructure.security.data.enums.Permission
import com.example.truckercore.model.modules.user.data.User

/**
 * Exception thrown when an unauthorized action is attempted by a user or service.
 *
 * This exception is typically used when a user or system does not have the necessary permissions
 * to perform a specific action. It is useful in scenarios where access control and security
 * are enforced, and an unauthorized action needs to be caught and handled appropriately.
 */
class UnauthorizedAccessException(val user: User, val permission: Permission) : Exception() {

    override val message: String
        get() = "User with ID: [${user.id}] does not have the " +
                "necessary permission [${permission.name}] to perform this action."

}