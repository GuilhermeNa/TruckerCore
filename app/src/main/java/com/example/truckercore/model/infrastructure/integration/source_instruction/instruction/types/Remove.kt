package com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.Instruction
import com.example.truckercore.model.shared.value_classes.GenericID
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.InstructionTag

data class Remove(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val id: GenericID
) : Instruction