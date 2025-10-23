package com.example.truckercore.layers.data.base.instruction.base

import com.example.truckercore.layers.data.base.instruction.firebase_impl.wrappers.ApiInstructionDequeWrapper
import com.example.truckercore.layers.data.base.instruction.for_app_impl.InstructionDeque

/**
 * Interface responsible for transforming raw [Instruction]s into domain-specific [ApiInstructionWrapper]s.
 *
 * This transformation layer acts as a parser between the generic instruction model and
 * an executable representation tailored to the target system (e.g., Firestore, REST APIs).
 *
 * @param R The back-end-specific instruction type produced by this interpreter.
 */
interface InstructionInterpreter {

    /**
     * Parses and converts the given deque of generic [Instruction]s into a
     * deque of executable [ApiInstructionWrapper]s.
     *
     * ### Example usage:
     * ```kotlin
     * val interpreter = FirestoreInstInterpreter(firestore)
     * val firebaseInstructions = interpreter(deque) // Transforms into FirebaseInstruction types
     * ```
     *
     * @param deque Queue of generic instructions to interpret.
     * @return A queue of interpreted, executable instructions.
     * @throws InterpreterException if interpretation fails
     */
    operator fun invoke(deque: InstructionDeque): ApiInstructionDequeWrapper

}