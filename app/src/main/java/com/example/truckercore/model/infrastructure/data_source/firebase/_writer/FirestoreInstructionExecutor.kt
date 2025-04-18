package com.example.truckercore.model.infrastructure.data_source.firebase._writer

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.FirestoreInstructionException
import com.example.truckercore.model.infrastructure.data_source.firebase.interpreter.FirestoreInterpreter
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.source_instruction.InstructionExecutor
import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.types.PutLazy
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreInstructionExecutor(
    private val firestore: FirebaseFirestore,
    private val interpreter: FirestoreInterpreter
) : InstructionExecutor {

    override suspend fun invoke(deque: ArrayDeque<Instruction>) {
        validateInstructions(deque)

        firestore.runTransaction { transaction ->
            val fbInstructions = interpreter.toFirebaseInstruction(deque)

            while (fbInstructions.isNotEmpty()) {
                val instruction = fbInstructions.removeFirst()
                instruction.execute(transaction)
            }

        }.await()

    }

    private fun validateInstructions(deque: ArrayDeque<Instruction>) {
        if (deque.isEmpty()) throw FirestoreInstructionException()

        if (deque.thereIsRepeatedTag()) throw FirestoreInstructionException()

        if (deque.isMissingLazyReference()) throw FirestoreInstructionException()
    }

    private fun ArrayDeque<Instruction>.thereIsRepeatedTag() =
        this.map { it.instructionTag }
            .groupingBy { it }
            .eachCount()
            .any { it.value > 2 }

    private fun ArrayDeque<Instruction>.isMissingLazyReference(): Boolean =
        this.filterIsInstance<PutLazy<*>>()
            .any { it.referenceIdFromTag !in this.map { t -> t.instructionTag }.toSet() }

}



