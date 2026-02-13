package com.example.truckercore.business_admin.layers.domain.use_case.employee

import com.example.truckercore.infra.security.Action
import com.example.truckercore.infra.security.Resource
import com.example.truckercore.infra.security.service.PermissionService
import com.example.truckercore.layers.domain.base.contracts.others.Employee
import com.example.truckercore.layers.domain.model.user.User

class GenerateRegistrationCodeUseCase(
    private val permissionService: PermissionService
) {

    fun invoke(user: User, employee: Employee) {
        if (hasPermission(user)) {

        } else {

        }
    }

    private fun hasPermission(user: User) =
        permissionService(user, Action.CREATE, Resource.PERMISSION_CODE)

}