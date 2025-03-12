package com.example.truckercore.model.infrastructure.security.permissions.service

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.user.entity.User

internal class PermissionServiceImpl: PermissionService {

    override fun canPerformAction(user: User, permission: Permission): Boolean {
        return user.hasPermission(permission)
    }

    override fun hasSystemAccess(user: User, central: BusinessCentral): Boolean {
        return central.userHasSystemAccess(user.id!!)
    }

}