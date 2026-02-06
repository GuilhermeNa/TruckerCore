package com.example.truckercore.business_admin.layers.domain.use_case.access

import com.example.truckercore.infra.security.Action
import com.example.truckercore.infra.security.Resource
import com.example.truckercore.infra.security.service.PermissionService
import com.example.truckercore.layers.domain.model.user.User

class AuthorizeEmployeeAccessUseCase(
    private val permissionService: PermissionService
) {

    fun invoke(authorizedUser: User, user: User) {
        if(hasPermission(authorizedUser)) {

        } else {

        }
    }

    private fun hasPermission(user: User) =
        permissionService(user, Action.APPROVE, Resource.PERMISSION_CODE)

}