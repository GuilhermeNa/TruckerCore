package com.example.truckercore.model.infrastructure.data_source.firebase._writer

import com.example.truckercore.model.infrastructure.data_source.firebase._writer.api_instructions.FirebaseDelete
import com.example.truckercore.model.infrastructure.data_source.firebase._writer.api_instructions.FirebaseInstruction
import com.example.truckercore.model.infrastructure.data_source.firebase._writer.api_instructions.FirebaseSet
import com.example.truckercore.model.infrastructure.data_source.firebase._writer.api_instructions.FirebaseUpdate
import com.example.truckercore.model.infrastructure.integration._writer.for_api.InstructionInterpreter
import com.example.truckercore.model.infrastructure.integration._writer.for_api.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types.Put
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types.PutLazy
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types.Remove
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types.Update
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.types.UpdateFields
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreInstInterpreter(
    private val firestore: FirebaseFirestore
) : InstructionInterpreter<FirebaseInstruction> {

    override fun invoke(deque: ArrayDeque<Instruction>): ArrayDeque<FirebaseInstruction> {
        val fbDeque = ArrayDeque<FirebaseInstruction>()

        deque.forEach { i ->
            val fbInstruction = when (i) {
                is Put -> getFirebaseSet(i)
                is PutLazy -> getFirebaseSet(i, fbDeque)
                is Remove -> getFirebaseDelete(i)
                is Update -> getFirebaseUpdate(i)
                is UpdateFields -> throw InvalidInstructionException(
                    "UpdateFields instruction not implemented yet."
                )

                else -> throw InvalidInstructionException(
                    "Firebase Instruction not implemented yet. Is not possible to interpret."
                )
            }
            fbDeque.add(fbInstruction)
        }

        return fbDeque
    }

    private fun getFirebaseSet(instruction: Put): FirebaseSet {
        return FirebaseSet(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName).document(),
            data = instruction.data
        )
    }

    private fun getFirebaseSet(
        instruction: PutLazy,
        deque: ArrayDeque<FirebaseInstruction>
    ): FirebaseSet {
        val hash = getHash(instruction.referenceIdFromTag, deque)

        return FirebaseSet(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName).document(),
            data = instruction.lazyData(hash)
        )
    }

    private fun getHash(
        requiredIds: List<InstructionTag>,
        deque: ArrayDeque<FirebaseInstruction>
    ): HashMap<InstructionTag, String> {
        val hash = hashMapOf<InstructionTag, String>()

        requiredIds.forEach { tag ->
            val id = deque.first { it.instructionTag == tag }.document.id
            hash[tag] = id
        }

        return hash
    }

    private fun getFirebaseDelete(instruction: Remove): FirebaseDelete {
        return FirebaseDelete(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName)
                .document(instruction.id.value)
        )
    }

    private fun getFirebaseUpdate(instruction: Update): FirebaseUpdate {
        return FirebaseUpdate(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName).document(instruction.id),
            data = instruction.data
        )
    }

}