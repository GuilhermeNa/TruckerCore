package com.example.truckercore.model.infrastructure.data_source.firebase.interpreter

import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.InstructionTag
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

interface FirebaseInstruction {

    val instructionTag: InstructionTag

    val document: DocumentReference

    fun execute(transaction: Transaction)

}