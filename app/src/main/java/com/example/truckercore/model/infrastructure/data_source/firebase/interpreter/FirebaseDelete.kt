package com.example.truckercore.model.infrastructure.data_source.firebase.interpreter

import com.example.truckercore.model.infrastructure.integration.instruction.InstructionTag
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

data class FirebaseDelete(
    override val instructionTag: InstructionTag,
    override val document: DocumentReference
) : FirebaseInstruction {

    override fun execute(transaction: Transaction) {
        transaction.delete(document)
    }

}