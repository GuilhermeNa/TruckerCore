package com.example.truckercore.model.infrastructure.security.data.collections

import com.example.truckercore.model.infrastructure.security.data.enums.Permission

class PermissionSet(private val data: Set<Permission>) {

    fun contains(permission: Permission) = data.contains(permission)

}