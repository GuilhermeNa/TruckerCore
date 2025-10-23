package com.example.truckercore.layers.domain.base.contracts.entity

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.enums.PersistenceState

interface BaseEntity {

    val id: ID

    val persistence: PersistenceState

    val idValue get() = id.value

    fun checkRelation(entity: Entity) {
        if (entity.companyId != id) throw DomainException.RuleViolation(
            "The received entity does not belong to this company(ID = $id). Entity: $entity."
        )
    }

}