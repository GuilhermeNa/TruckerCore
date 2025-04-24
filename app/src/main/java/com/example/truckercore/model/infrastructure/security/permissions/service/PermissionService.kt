package com.example.truckercore.model.infrastructure.security.permissions.service

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.user.data.User

/**
 * Interface that defines a service to check if a user has the required permission to perform an action.
 * It serves as an abstraction layer for permission validation logic.
 */
interface PermissionService {

    /**
     * Checks if a user has the required permission to perform a specific action.
     *
     * @param user The user whose permission is being checked.
     * @param permission The permission required to perform the action.
     * @return A boolean indicating if the user has the permission (true) or not (false).
     */
    fun canPerformAction(user: User, permission: Permission): Boolean

    /**
     * Checks if a user has access to the system in a specific Business Central.
     *
     * @param user The user whose system access is being checked.
     * @param central The Business Central instance where the access is being verified.
     * @return A boolean value indicating whether the user has system access (true) or not (false).
     */
    fun canAccessSystem(user: User, central: Company): Boolean

}