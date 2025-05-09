/*
package com.example.truckercore.unit.model.infrastructure.integration.writer.for_app.instruction

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.exceptions.InvalidInstructionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class InstructionTagTest {

    @Test
    fun `should create InstructionTag when value is not blank`() {
        // Arrange
        val tagValue = "INSTR-001"

        // Act
        val tag = InstructionTag(tagValue)

        // Assert
        assertEquals(tagValue, tag.value)
    }

    @Test
    fun `should throw InstructionException when value is blank`() {
        // Arrange
        val blankValue = "   "

        // Act & Assert
        val exception = assertThrows<InvalidInstructionException> {
            InstructionTag(blankValue)
        }

        assertEquals(
            "Instruction tag must not be blank. Each instruction requires a unique, non-empty tag for transaction reference tracking.",
            exception.message
        )
    }

}*/
