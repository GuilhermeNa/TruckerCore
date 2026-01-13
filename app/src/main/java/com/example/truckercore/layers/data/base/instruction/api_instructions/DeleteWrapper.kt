package com.example.truckercore.layers.data.base.instruction.api_instructions

import com.example.truckercore.layers.data.base.instruction._contracts.ApiInstructionWrapper
import com.example.truckercore.layers.data.base.instruction._contracts.Transactional
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

/**
 * Represents a Firestore delete operation to be executed in a transaction.
 *
 * This instruction deletes the specified [document] from the Firestore collection.
 *
 * @param document The Firestore document to be deleted.
 */
data class DeleteWrapper(
    override val document: DocumentReference
) : ApiInstructionWrapper, Transactional {

    override fun execute(transaction: Transaction) {
        transaction.delete(document)
    }

}