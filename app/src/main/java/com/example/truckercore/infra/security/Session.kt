package com.example.truckercore.infra.security

import com.example.truckercore.layers.domain.model.access.Access
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.model.user.User

data class Session(
    val user: User,
    val access: Access,
    val company: Company
) {

    init {
        company.checkRelation(user)
        company.checkRelation(access)
    }

}