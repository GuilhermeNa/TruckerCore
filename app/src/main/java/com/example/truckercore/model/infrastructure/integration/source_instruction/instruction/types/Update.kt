package com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.InstructionTag
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto

data class Update<T : BaseDto>(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val data: T
) : Instruction {

    val id  get() = data.id!!

    init {
        if(data.id.isNullOrBlank()) throw InvalidInstructionException(
            "The data object must have a valid non-null and non-blank ID for an update instruction."
        )
    }

}