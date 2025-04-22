package com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.exceptions.InstructionException
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.InstructionTag
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto

data class Update(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val data: BaseDto
) : Instruction {

    val id  get() = data.id!!

    init {
        if(data.id.isNullOrBlank()) throw InstructionException(
            "The data object must have a valid non-null and non-blank ID for an update instruction."
        )
    }

}