package com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.InstructionTag

data class UpdateFields(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val fields: Map<String, Any>
) : Instruction