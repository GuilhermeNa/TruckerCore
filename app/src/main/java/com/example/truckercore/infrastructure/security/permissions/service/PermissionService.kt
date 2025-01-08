package com.example.truckercore.infrastructure.security.permissions.service

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.entity.User

/**
 * Interface that defines a service to check if a user has the required permission to perform an action.
 * It serves as an abstraction layer for permission validation logic.
 */
internal interface PermissionService {

    /**
     * Checks if a user has the required permission to perform a specific action.
     *
     * @param user The user whose permission is being checked.
     * @param permission The permission required to perform the action.
     * @return A boolean indicating if the user has the permission (true) or not (false).
     */
    fun canPerformAction(user: User, permission: Permission): Boolean
}