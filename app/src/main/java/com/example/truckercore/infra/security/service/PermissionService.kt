package com.example.truckercore.infra.security.service

import com.example.truckercore.data.infrastructure.security.contracts.Authorizable
import com.example.truckercore.core.security.contracts.SystemManager
import com.example.truckercore.data.infrastructure.security.data.enums.Permission

/**
 * Interface that defines a service to check if a user has the required permission to perform an action.
 * It serves as an abstraction layer for permission validation logic.
 */
interface PermissionService {

    /**
     * Checks if a user has the required permission to perform a specific action.
     *
     * @param authorizable The user whose permission is being checked.
     * @param permission The permission required to perform the action.
     * @return A boolean indicating if the user has the permission (true) or not (false).
     */
    fun canPerformAction(authorizable: Authorizable, permission: Permission): Boolean

    /**
     * Checks if a user has access to the system in a specific Business Central.
     *
     * @param user The user whose system access is being checked.
     * @param system The System instance where the access is being verified.
     * @return A boolean value indicating whether the user has system access (true) or not (false).
     */
    fun canAccessSystem(authorizable: Authorizable, system: com.example.truckercore.core.security.contracts.SystemManager): Boolean

}