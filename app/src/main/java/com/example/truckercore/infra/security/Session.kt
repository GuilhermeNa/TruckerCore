package com.example.truckercore.infra.security

import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.model.user.User

data class Session(
    val company: Company,
    val user: User
) {

    init {
        company.checkFK(user.companyId)
    }

}