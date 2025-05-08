package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.exceptions.InstructionException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto

/**
 * Data class representing an "Update" instruction, which is used to update an existing item in a collection
 * with new data.
 *
 * @param instructionTag The unique identifier for this instruction.
 * @param collection The target collection where the item will be updated.
 * @param data The new data for the item to be updated, which must include a valid ID.
 */
data class Update(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val data: BaseDto
) : Instruction {

    /**
     * The ID of the item to be updated, which is extracted from the [data] object.
     */
    val id get() = data.id!!

    /**
     * Initializes the instruction and validates that the [data] object contains a valid ID.
     * If the ID is null or blank, an [InstructionException] is thrown.
     */
    init {
        if (data.id.isNullOrBlank()) throw InstructionException(
            "The data object must have a valid non-null and non-blank ID for an update instruction."
        )
    }

}