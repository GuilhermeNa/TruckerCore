package com.example.truckercore.model.infrastructure.security.service

import com.example.truckercore.model.infrastructure.security.contracts.Authorizable
import com.example.truckercore.model.infrastructure.security.contracts.SystemManager
import com.example.truckercore.model.infrastructure.security.data.enums.Permission

internal class PermissionServiceImpl : PermissionService {

    override fun canPerformAction(authorizable: Authorizable, permission: Permission): Boolean {
        return authorizable.hasPermission(permission)
    }

    override fun canAccessSystem(authorizable: Authorizable, system: SystemManager): Boolean {
        val accessKey = authorizable.accessKey()
        return system.isKeyValid(accessKey)
    }

}