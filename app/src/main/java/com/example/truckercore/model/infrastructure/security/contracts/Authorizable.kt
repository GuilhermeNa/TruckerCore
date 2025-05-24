package com.example.truckercore.model.infrastructure.security.contracts

import com.example.truckercore.model.infrastructure.security.data.enums.Permission
import com.example.truckercore.model.infrastructure.security.data.Profile
import com.example.truckercore.model.modules._shared.contracts.entity.ID
import com.example.truckercore.model.infrastructure.security.data.Key

interface Authorizable {

    val id: ID

    val profile: Profile

    fun hasPermission(permission: Permission): Boolean {
        return profile.hasPermission(permission)
    }

    fun accessKey(): Key = Key(id.value)

}