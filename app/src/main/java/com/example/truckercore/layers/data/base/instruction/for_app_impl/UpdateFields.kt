package com.example.truckercore.layers.data.base.instruction.for_app_impl

import com.example.truckercore.core.config.collections.CollectionResolver
import com.example.truckercore.infra.instruction.base.Instruction
import com.example.truckercore.layers.domain.base.contracts.entity.ID

/**
 * Data class representing an "UpdateFields" instruction, which is used to update specific fields of an item
 * in a collection. Unlike the `Update` instruction, which requires the complete data, `UpdateFields` allows
 * updating a subset of fields in the existing item.
 *
 * @param instructionTag The unique identifier for this instruction.
 * @param collection The target collection where the item will be updated.
 * @param fields A map representing the fields to be updated, where keys are field names and values are the new values.
 */
data class UpdateFields(
    val id: ID,
    val fields: Map<String, Any>
) : Instruction {

    override fun getId(): String = id.value

    override fun getCollection(): String = CollectionResolver(id).name

}