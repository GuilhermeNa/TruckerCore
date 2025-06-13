package com.example.truckercore.model.infrastructure.security.data.collections

import com.example.truckercore.model.infrastructure.security.data.enums.Permission

class PermissionSet(private val _data: Set<Permission>) {

    fun data() = _data

    fun contains(permission: Permission) = _data.contains(permission)

}