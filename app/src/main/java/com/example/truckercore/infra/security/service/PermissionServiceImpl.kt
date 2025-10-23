package com.example.truckercore.infra.security.service

import com.example.truckercore.data.infrastructure.security.contracts.Authorizable
import com.example.truckercore.core.security.contracts.SystemManager
import com.example.truckercore.data.infrastructure.security.data.enums.Permission

internal class PermissionServiceImpl :
    com.example.truckercore.core.security.service.PermissionService {

    override fun canPerformAction(authorizable: Authorizable, permission: Permission): Boolean {
        return authorizable.hasPermission(permission)
    }

    override fun canAccessSystem(authorizable: Authorizable, system: com.example.truckercore.core.security.contracts.SystemManager): Boolean {
        val accessKey = authorizable.accessKey()
        return system.isKeyValid(accessKey)
    }

}