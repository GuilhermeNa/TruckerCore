package com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction
import com.example.truckercore.model.shared.value_classes.GenericID
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.InstructionTag

/**
 * Data class representing a "Remove" instruction, which is used to delete a specific item from a collection
 * identified by its unique ID.
 *
 * @param instructionTag The unique identifier for this instruction.
 * @param collection The target collection where the item will be removed.
 * @param id The ID of the item to be removed from the collection.
 */
data class Remove(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val id: GenericID
) : Instruction