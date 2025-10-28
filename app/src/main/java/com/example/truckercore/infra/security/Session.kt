package com.example.truckercore.infra.security

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.model.user.User

data class Session(
    val user: User,
    val company: Company
) {

    init {
        if (user.companyId != company.id) throw DomainException.RuleViolation(
            "The received entity does not belong to this entity. This: $this. Received: $entity."
        )
    }

}