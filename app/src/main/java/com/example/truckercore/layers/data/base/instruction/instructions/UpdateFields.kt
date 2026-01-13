package com.example.truckercore.layers.data.base.instruction.instructions

import com.example.truckercore.core.config.collections.CollectionResolver
import com.example.truckercore.layers.data.base.instruction._contracts.Instruction
import com.example.truckercore.layers.domain.base.contracts.entity.ID

/**
 * Represents a partial update instruction for an existing entity.
 *
 * This instruction updates only the specified [fields] of an entity identified by [id],
 * without requiring the full object representation.
 *
 * @param id The identifier of the entity to be updated.
 * @param fields A map containing the field names and their new values.
 */
data class UpdateFields(
    val id: ID,
    val fields: Map<String, Any>
) : Instruction {

    override fun getId(): String = id.value

    override fun getCollection(): String = CollectionResolver(id).name

}