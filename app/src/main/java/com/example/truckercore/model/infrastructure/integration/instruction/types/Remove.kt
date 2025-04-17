package com.example.truckercore.model.infrastructure.integration.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.instruction.Instruction
import com.example.truckercore.model.shared.value_classes.GenericID
import com.example.truckercore.model.infrastructure.integration.instruction.InstructionTag

data class Remove(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val id: GenericID
) : Instruction