package com.example.truckercore.layers.data.base.instruction.api_instructions

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.instruction._contracts.ApiInstructionWrapper
import com.example.truckercore.layers.data.base.instruction._contracts.Transactional
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

/**
 * Represents a Firestore update operation that fully replaces the document's data.
 *
 * While semantically similar to [SetWrapper], this class may be extended in the future
 * to support partial updates or field-specific modifications.
 *
 * @param document The firebase document reference to update.
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