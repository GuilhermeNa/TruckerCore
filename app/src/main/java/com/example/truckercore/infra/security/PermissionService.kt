package com.example.truckercore.infra.security

import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.domain.model.user.User

object PermissionService {

    fun canHire(user: User): Boolean {
        return user.access.role == Role.ADMIN
    }

    fun canFire(user: User): Boolean {
        return user.access.role == Role.ADMIN
    }

    fun canUpdateAccess(user: User): Boolean {
        return user.access.role == Role.ADMIN
    }

    fun canEditAnotherEmployee(user: User): Boolean {
        return user.access.role == Role.ADMIN || user.access.role == Role.STAFF
    }

    fun canRegisterUserKey(user: User): Boolean {
        return user.access.role == Role.ADMIN
    }

}