package com.example.truckercore.unit.model.infrastructure.data_source.firebase.writer.api_instructions

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseSet
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class FirebaseSetTest {

    @Test
    fun `execute should call set from transaction`() {
        // Arrange
        val documentReference = mockk<DocumentReference>(relaxed = true)
        val tag = InstructionTag("Tag")
        val dto = FakeDto()
        val transaction = mockk<Transaction>(relaxed = true)

        val firebaseSet = FirebaseSet(
            document = documentReference,
            instructionTag = tag,
            data = dto
        )

        // Act
        firebaseSet.execute(transaction)

        // Assert
        verify(exactly = 1) { transaction.set(documentReference, dto) }
    }

}