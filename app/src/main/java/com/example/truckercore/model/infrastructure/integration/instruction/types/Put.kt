package com.example.truckercore.model.infrastructure.integration.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.instruction.Instruction
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto

data class Put<T : BaseDto>(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val data: T
) : Instruction