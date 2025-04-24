package com.example.truckercore.model.infrastructure.integration.writer.for_api

import com.example.truckercore.model.infrastructure.integration.writer.for_api.exceptions.InstructionExecutorException
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction

/**
 * Interface responsible for transforming raw [Instruction]s into domain-specific [ApiInstruction]s.
 *
 * This transformation layer acts as a parser between the generic instruction model and
 * an executable representation tailored to the target system (e.g., Firestore, REST APIs).
 *
 * @param R The back-end-specific instruction type produced by this interpreter.
 */
interface InstructionInterpreter<R : ApiInstruction> {

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
     * @throws InstructionExecutorException if interpretation fails
     */
    operator fun <T : Instruction> invoke(deque: ArrayDeque<T>): ArrayDeque<R>

}