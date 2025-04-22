package com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto

/**
 * Data class representing a "Put" instruction, which is typically used to insert data in a specific collection.
 *
 * @param instructionTag The unique identifier for this instruction.
 * @param collection The target collection where the data will be inserted.
 * @param data The data that will be inserted in the collection.
 */
data class Put(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val data: BaseDto
) : Instruction