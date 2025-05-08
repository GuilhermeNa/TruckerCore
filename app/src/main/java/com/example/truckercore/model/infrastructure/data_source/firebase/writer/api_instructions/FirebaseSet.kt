package com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

/**
 * Represents a Firestore insertion (or full replacement) operation.
 *
 * This instruction writes the provided [data] to the specified [document], replacing any existing content.
 *
 * @param instructionTag A unique identifier for reference or debugging.
 * @param document The target Firestore document.
 * @param data The data to be written, typically a DTO serialized into Firestore-compatible format.
 */
data class FirebaseSet(
    override val instructionTag: InstructionTag,
    override val document: DocumentReference,
    val data: BaseDto,
) : FirebaseInstruction {

    override fun execute(transaction: Transaction) {
        transaction.set(document, data)
    }

}