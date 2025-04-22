package com.example.truckercore.model.infrastructure.data_source.firebase._writer.api_instructions

import com.example.truckercore.model.infrastructure.integration._writer.for_api.ApiInstruction
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

interface FirebaseInstruction: ApiInstruction {

    val document: DocumentReference

    fun execute(transaction: Transaction)

}