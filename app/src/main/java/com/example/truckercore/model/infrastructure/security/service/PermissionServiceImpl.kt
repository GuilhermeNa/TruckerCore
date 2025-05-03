package com.example.truckercore.model.infrastructure.security.service

import com.example.truckercore.model.infrastructure.security.enums.Permission
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.user.data.User

internal class PermissionServiceImpl: PermissionService {

    override fun canPerformAction(user: User, permission: Permission): Boolean {
        return user.hasPermission(permission)
    }

    override fun canAccessSystem(user: User, central: Company): Boolean {
        return central.userHasSystemAccess(user.id)
    }

}