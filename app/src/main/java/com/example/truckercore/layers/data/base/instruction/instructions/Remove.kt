package com.example.truckercore.layers.data.base.instruction.instructions

import com.example.truckercore.core.config.collections.CollectionResolver
import com.example.truckercore.layers.data.base.instruction._contracts.Instruction
import com.example.truckercore.layers.domain.base.contracts.entity.ID

/**
 * Represents a remove (delete) instruction within the system.
 *
 * This instruction deletes an entity identified by the provided [ID] from its
 * corresponding collection.
 *
 * The target collection is resolved dynamically using [CollectionResolver] based on
 * the provided [ID].
 *
 * @param id The identifier of the entity to be removed.
 */
data class Remove(private val id: ID) : Instruction {

    override fun getId(): String = id.value

    override fun getCollection(): String = CollectionResolver(id).name

}