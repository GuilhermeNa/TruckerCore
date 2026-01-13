package com.example.truckercore.layers.data.base.instruction._contracts

import com.example.truckercore.layers.data.base.instruction.collections.ApiInstructionDeque
import com.example.truckercore.layers.data.base.instruction.collections.InstructionDeque

/**
 * Interface responsible for transforming raw [Instruction]s into domain-specific [ApiInstructionWrapper]s.
 *
 * This transformation layer acts as a parser between the generic instruction model and
 * an executable representation tailored to the target system (e.g., Firestore, REST APIs).
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
     */
    operator fun invoke(deque: InstructionDeque): ApiInstructionDeque

}