package com.example.truckercore.model.infrastructure.integration.instruction

import com.example.truckercore.model.infrastructure.integration.instruction.exceptions.InvalidInstructionTagExceptions

@JvmInline
value class InstructionTag(val value: String) {

    init {
        if (value.isBlank()) throw InvalidInstructionTagExceptions(
            "Instruction tag must not be blank. Each instruction requires a unique," +
                    " non-empty tag for transaction reference tracking."
        )
    }

}