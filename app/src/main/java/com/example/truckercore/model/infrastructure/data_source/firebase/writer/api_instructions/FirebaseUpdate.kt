package com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

/**
 * Represents a Firestore update operation that fully replaces the document's data.
 *
 * While semantically similar to [FirebaseSet], this class may be extended in the future
 * to support partial updates or field-specific modifications.
 *
 * @param instructionTag A unique tag for tracking or dependency resolution.
 * @param document The document reference to update.
 * @param data The new data to apply to the document.
 */
data class FirebaseUpdate(
    override val instructionTag: InstructionTag,
    override val document: DocumentReference,
    val data: BaseDto,
) : FirebaseInstruction {

    override fun execute(transaction: Transaction) {
        transaction.set(document, data)
    }

}