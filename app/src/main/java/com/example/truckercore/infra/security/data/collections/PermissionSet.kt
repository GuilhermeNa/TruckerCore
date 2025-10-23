package com.example.truckercore.infra.security.data.collections

import com.example.truckercore.data.infrastructure.security.data.enums.Permission

class PermissionSet(private val _data: Set<Permission>) {

    fun data() = _data

    fun contains(permission: Permission) = _data.contains(permission)

}