package com.example.truckercore.model.infrastructure.security.configs

import com.example.truckercore.model.infrastructure.security.data.collections.PermissionSet
import com.example.truckercore.model.infrastructure.security.data.enums.Permission
import com.example.truckercore.model.infrastructure.security.data.enums.Role

/**
 * Object to manage and retrieve default permissions based on the user's level.
 * This object holds predefined permissions for different user roles such as MASTER, ADMIN, MODERATOR, and DRIVER.
 */
object DefaultPermissionsProvider {

    /**
     * Retrieves the set of permissions based on the user's level.
     *
     * @param user The user whose permissions will be retrieved.
     * @return A set of permissions granted to the user based on their level.
     */
    operator fun invoke(role: Role): PermissionSet {
        val permissions = when (role) {
            Role.ADMIN -> masterPermissions
            Role.MANAGER -> adminPermissions
            Role.DRIVER -> moderatorPermissions
            Role.AUTONOMOUS -> userPermissions
        }
        return PermissionSet(permissions)
    }

    private val masterPermissions = hashSetOf(
        Permission.CREATE_USER, Permission.UPDATE_USER, Permission.VIEW_USER,
        Permission.ARCHIVE_USER, Permission.DELETE_USER,
        Permission.CREATE_PERSONAL_DATA, Permission.UPDATE_PERSONAL_DATA,
        Permission.ARCHIVE_PERSONAL_DATA, Permission.DELETE_PERSONAL_DATA,
        Permission.VIEW_PERSONAL_DATA
    )

    private val adminPermissions = hashSetOf(
        Permission.CREATE_USER, Permission.UPDATE_USER, Permission.VIEW_USER,
        Permission.ARCHIVE_USER, Permission.DELETE_USER,
        Permission.CREATE_PERSONAL_DATA, Permission.UPDATE_PERSONAL_DATA,
        Permission.ARCHIVE_PERSONAL_DATA, Permission.DELETE_PERSONAL_DATA,
        Permission.VIEW_PERSONAL_DATA
    )

    private val moderatorPermissions = hashSetOf(Permission.VIEW_USER)

    private val userPermissions = hashSetOf(Permission.VIEW_PERSONAL_DATA)

}