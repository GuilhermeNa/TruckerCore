package com.example.truckercore.model.infrastructure.security.data

import com.example.truckercore.model.infrastructure.security.data.enums.Permission
import com.example.truckercore.model.infrastructure.security.data.enums.Role

data class ProfileDto(
    val role: Role? = null,
    val permissions: List<Permission>? = null
) {

}