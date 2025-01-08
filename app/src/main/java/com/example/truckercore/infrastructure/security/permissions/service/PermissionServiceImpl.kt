package com.example.truckercore.infrastructure.security.permissions.service

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.entity.User

internal class PermissionServiceImpl: PermissionService {

    override fun canPerformAction(user: User, permission: Permission): Boolean {
        return user.hasPermission(permission)
    }

}