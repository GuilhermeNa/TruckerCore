package com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions

import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.InstructionTag
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

/**
 * Represents a Firestore delete operation to be executed in a transaction.
 *
 * This instruction deletes the specified [document] from the Firestore collection.
 *
 * @param instructionTag A unique identifier used for traceability or reference resolution.
 * @param document The Firestore document to be deleted.
 */
data class FirebaseDelete(
    override val instructionTag: InstructionTag,
    override val document: DocumentReference
) : FirebaseInstruction {

    override fun execute(transaction: Transaction) {
        transaction.delete(document)
    }

}