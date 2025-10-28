package com.example.truckercore.layers.domain.base.contracts.entity

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.enums.Status

interface BaseEntity {

    val id: ID

    val status: Status

    val idValue get() = id.value

    fun checkFK(receivedId: ID) {
        if (receivedId != id) throw DomainException.RuleViolation(
            "The received entity does not belong to this entity. Expected id: $id and received: $receivedId."
        )
    }

}