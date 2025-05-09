package com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions

import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.contracts.Transactional
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.contracts.ApiInstruction
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
    override val document: DocumentReference
) : ApiInstruction, Transactional {

    override fun execute(transaction: Transaction) {
        transaction.delete(document)
    }

}