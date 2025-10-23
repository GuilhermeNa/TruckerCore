package com.example.truckercore.layers.data.base.instruction.firebase_impl.wrappers

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.instruction.base.ApiInstructionWrapper
import com.example.truckercore.layers.data.base.instruction.firebase_impl.base.Transactional
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

/**
 * Represents a Firestore update operation that fully replaces the document's data.
 *
 * While semantically similar to [SetWrapper], this class may be extended in the future
 * to support partial updates or field-specific modifications.
 *
 * @param instructionTag A unique tag for tracking or dependency resolution.
 * @param document The document reference to update.
 * @param data The new data to apply to the document.
 */
data class UpdateWrapper(
    override val document: DocumentReference,
    val data: BaseDto,
) : ApiInstructionWrapper, Transactional {

    override fun execute(transaction: Transaction) {
        transaction.set(document, data)
    }

}