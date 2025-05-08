package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto

/**
 * Data class representing a "PutLazy" instruction, which is used to insert data in a collection
 * where the data is lazily generated based on references to other instructions.
 *
 * @param instructionTag The unique identifier for this instruction.
 * @param collection The target collection where the data will be inserted.
 * @param referenceIdFromTag A list of instruction tags that represent references to other instructions.
 *                          These references are used to resolve the data needed for this operation.
 * @param lazyData A function that generates the data to be inserted, based on a map of resolved
 *                 references (a mapping of instruction tags to their respective resolved data).
 */
data class PutLazy(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val referenceIdFromTag: List<InstructionTag>,
    val lazyData: (HashMap<InstructionTag, String>) -> BaseDto,
) : Instruction