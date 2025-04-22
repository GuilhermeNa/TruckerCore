package com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto

data class Put(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val data: BaseDto
) : Instruction