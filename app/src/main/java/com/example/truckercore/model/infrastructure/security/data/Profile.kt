package com.example.truckercore.model.infrastructure.security.data

import com.example.truckercore.model.infrastructure.security.data.collections.PermissionSet
import com.example.truckercore.model.infrastructure.security.data.enums.Permission
import com.example.truckercore.model.infrastructure.security.data.enums.Role

class Profile(
    val role: Role,
    val permissions: PermissionSet
) {

    fun hasPermission(permission: Permission): Boolean {
        return permissions.contains(permission)
    }

}


