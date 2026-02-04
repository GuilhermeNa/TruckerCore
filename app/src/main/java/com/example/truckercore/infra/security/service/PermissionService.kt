package com.example.truckercore.infra.security.service

import com.example.truckercore.infra.security.Action
import com.example.truckercore.infra.security.Resource
import com.example.truckercore.layers.domain.model.user.User

/**
 * Interface that defines a service to check if a user has the required permission to perform an action.
 * It serves as an abstraction layer for permission validation logic.
 */
interface PermissionService {

    fun hasPermission(user: User, action: Action, resource: Resource): Boolean

    operator fun invoke(user: User, action: Action, resource: Resource): Boolean

}