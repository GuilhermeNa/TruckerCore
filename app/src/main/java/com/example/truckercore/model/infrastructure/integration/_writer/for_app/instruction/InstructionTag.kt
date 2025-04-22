package com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction

import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.exceptions.InstructionException

@JvmInline
value class InstructionTag(val value: String) {

    init {
        if (value.isBlank()) throw InstructionException(
            "Instruction tag must not be blank. Each instruction requires a unique," +
                    " non-empty tag for transaction reference tracking."
        )
    }

}