package com.example.truckercore.model.infrastructure.data_source.firebase.writer

import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseInstruction
import com.example.truckercore.model.infrastructure.integration.writer.for_api.InstructionExecutor
import com.example.truckercore.model.infrastructure.integration.writer.for_api.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.types.PutLazy
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreExecutor(
    private val firestore: FirebaseFirestore,
    interpreter: FirestoreInstInterpreter
) : InstructionExecutor<FirebaseInstruction>(interpreter) {

    override suspend fun invoke(deque: ArrayDeque<Instruction>) {
        validateInstructions(deque)

        // Executes all instructions within a Firestore transaction
        firestore.runTransaction { transaction ->
            // Transforms the raw instructions into Firebase-specific instructions
            val fbInstructions = interpreter(deque)

            // Executes each instruction inside the transaction
            while (fbInstructions.isNotEmpty()) {
                val instruction = fbInstructions.removeFirst()
                instruction.execute(transaction)
            }
        }.await() // Suspends until the transaction completes

    }

    // Ensures the instruction queue meets all preconditions before execution
    private fun validateInstructions(deque: ArrayDeque<Instruction>) {
        if (deque.isEmpty()) throw InvalidInstructionException(
            "Instruction deque must not be empty. At least one instruction is required for execution."
        )

        if (deque.thereIsRepeatedTag()) throw InvalidInstructionException(
            "Each instruction must have a unique instructionTag. Duplicate tags found in the deque."
        )

        if (deque.isMissingLazyReference()) throw InvalidInstructionException(
            "Missing instructionTag(s) required by one or more PutLazy instructions. " +
                    "Ensure all referenced instructions are present in the deque."
        )
    }

    // Checks if there are duplicate instruction tags in the queue
    private fun ArrayDeque<Instruction>.thereIsRepeatedTag() =
        this.map { it.instructionTag }
            .groupingBy { it }
            .eachCount()
            .any { it.value > 1 }

    // Checks if any PutLazy instruction is referencing a missing instruction
    private fun ArrayDeque<Instruction>.isMissingLazyReference(): Boolean {
        val putLazies = this.filterIsInstance<PutLazy>()

        return if (putLazies.isNotEmpty()) {
            val receivedTags = this.map { it.instructionTag }
            val requiredInLazyTags = putLazies.flatMap { it.referenceIdFromTag }
            !receivedTags.containsAll(requiredInLazyTags)
        } else false
    }

}



