package com.example.truckercore.layers.data.base.instruction.firebase_impl.wrappers

import com.example.truckercore.layers.data.base.instruction.base.ApiInstructionWrapper
import com.example.truckercore.layers.data.base.instruction.firebase_impl.base.Transactional
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
data class DeleteWrapper(override val document: DocumentReference) : ApiInstructionWrapper,
    Transactional {

    override fun execute(transaction: Transaction) {
        transaction.delete(document)
    }

}