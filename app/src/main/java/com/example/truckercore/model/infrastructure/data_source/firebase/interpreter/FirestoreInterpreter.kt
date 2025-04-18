package com.example.truckercore.model.infrastructure.data_source.firebase.interpreter

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.FirestoreInstructionException
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.Instruction
import com.example.truckercore.model.shared.value_classes.GenericID
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.types.Put
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.types.PutLazy
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.types.Remove
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.types.Update
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.types.UpdateFields
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreInterpreter(private val firestore: FirebaseFirestore) {

    fun toFirebaseInstruction(deque: ArrayDeque<Instruction>): ArrayDeque<FirebaseInstruction> {
        val fbDeque = ArrayDeque<FirebaseInstruction>()

        deque.forEach { i ->
            val fbInstruction = when (i) {
                is Put<*> -> getFirebaseSet(i)
                is PutLazy<*> -> getFirebaseSet(i, fbDeque)
                is Remove -> getFirebaseDelete(i)
                is Update<*> -> getFirebaseUpdate(i)
                is UpdateFields<*> -> TODO()

                else -> throw FirestoreInstructionException(
                    "Firebase Instruction not implemented yet. Is not possible to interpret."
                )

            }

            fbDeque.add(fbInstruction)
        }

        return fbDeque
    }

    private fun getFirebaseSet(instruction: Put<*>): FirebaseSet<*> {
        return FirebaseSet(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName).document(),
            data = instruction.data
        )
    }

    private fun getFirebaseSet(
        instruction: PutLazy<*>,
        deque: ArrayDeque<FirebaseInstruction>
    ): FirebaseSet<*> {
        return FirebaseSet(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName).document(),
            data = instruction.lazyData(getId(instruction, deque))
        )
    }

    private fun getId(inst: PutLazy<*>, deque: ArrayDeque<FirebaseInstruction>): GenericID {
        val id = deque.first { it.instructionTag == inst.referenceIdFromTag }.document.id
        return GenericID(id)
    }

    private fun getFirebaseDelete(instruction: Remove): FirebaseDelete {
        return FirebaseDelete(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName)
                .document(instruction.id.value)
        )
    }

    private fun getFirebaseUpdate(instruction: Update<*>): FirebaseUpdate<*> {
        return FirebaseUpdate(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName).document(instruction.id),
            data = instruction.data
        )
    }

}