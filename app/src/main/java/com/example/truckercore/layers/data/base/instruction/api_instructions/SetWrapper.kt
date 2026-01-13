package com.example.truckercore.layers.data.base.instruction.api_instructions

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.instruction._contracts.ApiInstructionWrapper
import com.example.truckercore.layers.data.base.instruction._contracts.Transactional
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

/**
 * Represents a Firestore insertion (or full replacement) operation.
 *
 * This instruction writes the provided [data] to the specified [document], replacing any existing content.
 *
 * @param document The target Firestore document.
 * @param data The data to be written, typically a DTO serialized into Firestore-compatible format.
 */
data class SetWrapper(
    override val document: DocumentReference,
    val data: BaseDto
) : ApiInstructionWrapper, Transactional {

    override fun execute(transaction: Transaction) {
        transaction.set(document, data)
    }

}