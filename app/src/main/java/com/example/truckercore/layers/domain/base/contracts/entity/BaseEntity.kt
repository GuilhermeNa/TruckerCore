package com.example.truckercore.layers.domain.base.contracts.entity

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.enums.Status

/**
 * Base contract for all entities in the domain.
 *
 * Provides:
 * - Identity
 * - Visibility status
 * - Foreign key validation support
 */
interface BaseEntity {

    val id: ID
    val status: Status

    /**
     * Convenience property to access the raw identifier value.
     */
    val idValue get() = id.value

    /**
     * Validates that a received foreign key belongs to this entity.
     *
     * @throws DomainException.RuleViolation if the IDs do not match
     */
    fun checkFK(receivedId: ID) {
        if (receivedId != id) {
            throw DomainException.RuleViolation(
                "The received entity does not belong to this entity. " +
                        "Expected id: $id and received: $receivedId."
            )
        }
    }
}