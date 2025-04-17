package com.example.truckercore.model.infrastructure.data_source.firebase.interpreter

import com.example.truckercore.model.infrastructure.integration.instruction.InstructionTag
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

data class FirebaseUpdate<T : BaseDto>(
    override val instructionTag: InstructionTag,
    override val document: DocumentReference,
    val data: T,
) : FirebaseInstruction {

    override fun execute(transaction: Transaction) {
        transaction.set(document, data)
    }

}