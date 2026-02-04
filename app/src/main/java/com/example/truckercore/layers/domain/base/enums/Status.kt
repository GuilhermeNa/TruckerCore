package com.example.truckercore.layers.domain.base.enums

/**
 * Represents the visibility status of an entity.
 *
 * This status controls whether the entity should be considered
 * in default database queries. It behaves as a soft delete mechanism.
 */
enum class Status {
    ACTIVE,
    INACTIVE
}