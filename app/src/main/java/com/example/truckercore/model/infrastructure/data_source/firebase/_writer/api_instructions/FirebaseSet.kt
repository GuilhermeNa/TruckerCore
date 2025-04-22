package com.example.truckercore.model.infrastructure.data_source.firebase._writer.api_instructions

import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.InstructionTag
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

data class FirebaseSet(
    override val instructionTag: InstructionTag,
    override val document: DocumentReference,
    val data: BaseDto,
): FirebaseInstruction {

    override fun execute(transaction: Transaction) {
        transaction.set(document, data)
    }

}