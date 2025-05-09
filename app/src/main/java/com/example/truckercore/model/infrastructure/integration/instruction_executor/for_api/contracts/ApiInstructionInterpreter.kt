package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.contracts

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.collections.ApiInstructionQueue
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.contracts.ApiInstruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions.InstructionInterpreterException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque

/**
 * Interface responsible for transforming raw [Instruction]s into domain-specific [ApiInstruction]s.
 *
 * This transformation layer acts as a parser between the generic instruction model and
 * an executable representation tailored to the target system (e.g., Firestore, REST APIs).
 *
 * @param R The back-end-specific instruction type produced by this interpreter.
 */
interface ApiInstructionInterpreter {

    /**
     * Parses and converts the given deque of generic [Instruction]s into a
     * deque of executable [ApiInstruction]s.
     *
     * ### Example usage:
     * ```kotlin
     * val interpreter = FirestoreInstInterpreter(firestore)
     * val firebaseInstructions = interpreter(deque) // Transforms into FirebaseInstruction types
     * ```
     *
     * @param deque Queue of generic instructions to interpret.
     * @return A queue of interpreted, executable instructions.
     * @throws InstructionInterpreterException if interpretation fails
     */
    operator fun invoke(deque: InstructionDeque): ApiInstructionQueue

}