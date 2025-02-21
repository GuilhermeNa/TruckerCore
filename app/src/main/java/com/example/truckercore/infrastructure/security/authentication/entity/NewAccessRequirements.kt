package com.example.truckercore.infrastructure.security.authentication.entity

import com.example.truckercore.modules.user.enums.PersonCategory

data class NewAccessRequirements(
    val businessCentral: String? = null,
    val uid: String,
    val name: String,
    val surname: String,
    val email: String,
    val personFlag: PersonCategory
)
