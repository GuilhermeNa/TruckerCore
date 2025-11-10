package com.example.truckercore.infra.security.service

import com.example.truckercore.infra.security.Action
import com.example.truckercore.infra.security.Resource
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.domain.model.user.User

internal class PermissionServiceImpl : PermissionService {

    override fun hasPermission(user: User, action: Action, resource: Resource): Boolean =
        when (user.access.role) {
            Role.ADMIN -> true
            Role.AUTONOMOUS -> true
            Role.STAFF -> handleStaffPermissions(action, resource)
            Role.DRIVER -> handleDriverPermissions(action, resource)
        }

    private fun handleStaffPermissions(action: Action, resource: Resource): Boolean =
        when {
            resource.isCompany() && !action.isRead() -> false
            resource.isUser() && !action.isRead() -> false
            action.isDelete() -> false
            else -> true
        }

    private fun handleDriverPermissions(action: Action, resource: Resource): Boolean =
        when {
            action.isRead() -> true
            resource.isDriveLicense() && !action.isRead() -> true
            else -> false
        }

}