package com.example.truckercore.model.infrastructure.integration._instruction.for_app

import com.example.truckercore.model.infrastructure.integration._instruction.for_app.exceptions.InvalidInstructionTagExceptions

@JvmInline
value class InstructionTag(val value: String) {

    init {
        if (value.isBlank()) throw InvalidInstructionTagExceptions(
            "Instruction tag must not be blank. Each instruction requires a unique," +
                    " non-empty tag for transaction reference tracking."
        )
    }

}