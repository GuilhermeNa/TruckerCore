package com.example.truckercore.unit.model.infrastructure.data_source.firebase.writer.api_instructions

import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseDelete
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.InstructionTag
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class FirebaseDeleteTest {

    @Test
    fun `execute should call delete from transaction`() {
        // Arrange
        val documentReference = mockk<DocumentReference>(relaxed = true)
        val tag = InstructionTag("Tag")
        val transaction = mockk<Transaction>(relaxed = true)

        val firebaseDelete = FirebaseDelete(document = documentReference, instructionTag = tag)

        // Act
        firebaseDelete.execute(transaction)

        // Assert
        verify(exactly = 1) { transaction.delete(documentReference) }
    }

}