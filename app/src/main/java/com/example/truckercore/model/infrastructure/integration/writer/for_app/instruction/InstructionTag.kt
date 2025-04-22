package com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction

import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.exceptions.InstructionException

/**
 * A value class representing a unique tag associated with an instruction.
 *
 * The `InstructionTag` serves as a unique identifier for each instruction within a transaction.
 *
 * @param value The unique string value of the instruction tag.
 * @throws InstructionException if the value is blank.
 */
@JvmInline
value class InstructionTag(val value: String) {

    init {
        if (value.isBlank()) throw InstructionException(
            "Instruction tag must not be blank. Each instruction requires a unique," +
                    " non-empty tag for transaction reference tracking."
        )
    }

}