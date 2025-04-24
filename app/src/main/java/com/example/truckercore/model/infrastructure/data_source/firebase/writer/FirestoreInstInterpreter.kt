package com.example.truckercore.model.infrastructure.data_source.firebase.writer

import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseDelete
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseInstruction
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseSet
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseUpdate
import com.example.truckercore.model.infrastructure.integration.writer.for_api.InstructionInterpreter
import com.example.truckercore.model.infrastructure.integration.writer.for_api.exceptions.InstructionNotImplementedException
import com.example.truckercore.model.infrastructure.integration.writer.for_api.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types.Put
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types.PutLazy
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types.Remove
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types.Update
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types.UpdateFields
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreInstInterpreter(
    private val firestore: FirebaseFirestore
) : InstructionInterpreter<FirebaseInstruction> {

    override fun <T : Instruction> invoke(deque: ArrayDeque<T>): ArrayDeque<FirebaseInstruction> {
        val fbDeque = ArrayDeque<FirebaseInstruction>()

        // Iterate through each instruction in the deque and interpret it into a Firestore-specific instruction
        deque.forEach { i ->
            val fbInstruction = when (i) {
                is Put -> getFirebaseSet(i) // Simple insert
                is PutLazy -> getFirebaseSet(i, fbDeque) // Insert with references to previous instructions
                is Remove -> getFirebaseDelete(i) // Deletion instruction
                is Update -> getFirebaseUpdate(i) // Full update
                is UpdateFields -> throw InvalidInstructionException(
                    // Not supported yet — throws domain-specific exception
                    "UpdateFields instruction not implemented yet."
                )

                else -> throw InstructionNotImplementedException(
                    // Catch-all for any unsupported instruction type
                    "Firebase Instruction not implemented yet. Is not possible to interpret."
                )
            }

            // Add the interpreted instruction to the output deque
            fbDeque.add(fbInstruction)
        }

        return fbDeque
    }

    // Builds a FirestoreSet instruction for simple Put operations
    private fun getFirebaseSet(instruction: Put): FirebaseSet {
        val document = firestore.collection(instruction.collectionName).document()
        val newData = instruction.data.copyWith(document.id)
        return FirebaseSet(
            instructionTag = instruction.instructionTag,
            document = document,
            data = newData
        )
    }

    // Builds a FirestoreSet instruction for PutLazy operations, resolving references from earlier instructions
    private fun getFirebaseSet(
        instruction: PutLazy,
        deque: ArrayDeque<FirebaseInstruction>
    ): FirebaseSet {
        // Extract referenced document IDs from previously interpreted instructions
        val hash = getHash(instruction.referenceIdFromTag, deque)

        val document = firestore.collection(instruction.collectionName).document()

        return FirebaseSet(
            instructionTag = instruction.instructionTag,
            document = document,
            data = instruction.lazyData(hash)
                .copyWith(id = document.id) // Lazy data depends on referenced document IDs
        )
    }

    // Resolves instruction tags to their corresponding document IDs
    private fun getHash(
        requiredIds: List<InstructionTag>,
        deque: ArrayDeque<FirebaseInstruction>
    ): HashMap<InstructionTag, String> {
        val hash = hashMapOf<InstructionTag, String>()

        requiredIds.forEach { tag ->
            // Look up the document ID of the previously interpreted instruction with the given tag
            val id = deque.first { it.instructionTag == tag }.document.id
            hash[tag] = id
        }

        return hash
    }

    // Converts a Remove instruction into a FirestoreDelete instruction
    private fun getFirebaseDelete(instruction: Remove): FirebaseDelete {
        return FirebaseDelete(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName)
                .document(instruction.id.value)
        )
    }

    // Converts an Update instruction into a FirestoreUpdate instruction
    private fun getFirebaseUpdate(instruction: Update): FirebaseUpdate {
        return FirebaseUpdate(
            instructionTag = instruction.instructionTag,
            document = firestore.collection(instruction.collectionName).document(instruction.id),
            data = instruction.data
        )
    }

}