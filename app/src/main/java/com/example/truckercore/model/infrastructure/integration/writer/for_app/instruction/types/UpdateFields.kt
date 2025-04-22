package com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.InstructionTag

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
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val fields: Map<String, Any>
) : Instruction