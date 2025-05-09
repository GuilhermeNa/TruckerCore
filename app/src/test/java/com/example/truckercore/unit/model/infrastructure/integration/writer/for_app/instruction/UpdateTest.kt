/*
package com.example.truckercore.unit.model.infrastructure.integration.writer.for_app.instruction

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Update
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UpdateTest {

    @Test
    fun `should initialize Update successfully when data has valid id`() {
        // Arrange
        val instructionTag = mockk<InstructionTag>(relaxed = true)
        val collection = mockk<Collection>()
        val dto = FakeDto(id = "123")

        // Act
        val update = Update(instructionTag, collection, dto)

        // Assert
        assertEquals("123", update.id)
        assertEquals(dto, update.data)
        assertEquals(instructionTag, update.instructionTag)
        assertEquals(collection, update.collection)
    }

    @Test
    fun `should throw InstructionException when data has null id`() {
        // Arrange
        val instructionTag = mockk<InstructionTag>(relaxed = true)
        val collection = mockk<Collection>()
        val dto = FakeDto(id = null)

        // Act & Assert
        val exception = assertThrows<InvalidInstructionException> {
            Update(instructionTag, collection, dto)
        }

        assertEquals(
            "The data object must have a valid non-null and non-blank ID " +
                    "for an update instruction.",
            exception.message
        )
    }

    @Test
    fun `should throw InstructionException when data has blank id`() {
        // Arrange
        val instructionTag = mockk<InstructionTag>(relaxed = true)
        val collection = mockk<Collection>()
        val dto = FakeDto(id = "")

        // Act & Assert
        val exception = assertThrows<InvalidInstructionException> {
            Update(instructionTag, collection, dto)
        }

        assertEquals(
            "The data object must have a valid non-null and non-blank ID for an update instruction.",
            exception.message
        )
    }

}*/
