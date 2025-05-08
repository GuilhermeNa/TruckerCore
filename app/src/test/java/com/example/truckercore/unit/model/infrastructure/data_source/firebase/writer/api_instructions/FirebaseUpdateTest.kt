package com.example.truckercore.unit.model.infrastructure.data_source.firebase.writer.api_instructions

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseUpdate
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class FirebaseUpdateTest {

    @Test
    fun `execute should call update from transaction`() {
        // Arrange
        val documentReference = mockk<DocumentReference>(relaxed = true)
        val tag = InstructionTag("Tag")
        val dto = FakeDto()
        val transaction = mockk<Transaction>(relaxed = true)

        val firebaseUpdate = FirebaseUpdate(
            document = documentReference,
            instructionTag = tag,
            data = dto
        )

        // Act
        firebaseUpdate.execute(transaction)

        // Assert
        verify(exactly = 1) { transaction.set(documentReference, dto) }
    }

}