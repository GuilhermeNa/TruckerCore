package com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.InstructionTag
import com.example.truckercore.model.modules.user.data.UserDto
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.example.truckercore.model.shared.value_classes.GenericID

data class PutLazy(
    override val instructionTag: InstructionTag,
    override val collection: Collection,
    val referenceIdFromTag: List<InstructionTag>,
    val lazyData: (HashMap<InstructionTag, String>) -> BaseDto,
) : Instruction