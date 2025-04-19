package com.example.truckercore.model.infrastructure.integration._instruction.for_app.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration._instruction.for_app.InstructionTag
import com.example.truckercore.model.infrastructure.integration._instruction.for_app.Instruction
import com.example.truckercore.model.shared.value_classes.GenericID
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto

data class PutLazy<T : BaseDto>(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val referenceIdFromTag: InstructionTag,
    val lazyData: (GenericID) -> T,
) : Instruction