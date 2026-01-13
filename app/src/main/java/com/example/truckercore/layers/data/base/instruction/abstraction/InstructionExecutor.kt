package com.example.truckercore.layers.data.base.instruction.abstraction

import com.example.truckercore.layers.data.base.instruction._contracts.ApiInstructionWrapper
import com.example.truckercore.layers.data.base.instruction._contracts.Instruction
import com.example.truckercore.layers.data.base.instruction._contracts.InstructionInterpreter
import com.example.truckercore.layers.data.base.instruction.collections.InstructionDeque

/**
 * Abstract class responsible for orchestrating the execution of a sequence of [Instruction]s.
 *
 * This executor receives a queue of raw instructions, validates them, and delegates their transformation
 * to a domain-specific [ApiInstructionInterpreter] for processing and execution (e.g., in a transactional context).
 *
 * Concrete implementations should define how instructions are executed (e.g., in Firestore, SQLite, etc.).
 *
 * @property interpreter The interpreter responsible for transforming raw [Instruction]s into [ApiInstructionWrapper]s.
 */
abstract class InstructionExecutor(protected val interpreter: InstructionInterpreter) {

    /**
     * Executes the given queue of instructions.
     *
     * ### Example usage:
     * ```kotlin
     * val executor = FirestoreInstructionExecutor(FirestoreInstInterpreter(firestore))
     * val deque = ArrayDeque<Instruction>().apply {
     *     add(Put(...))
     *     add(Remove(...))
     * }
     * executor(deque) // Executes all instructions inside a Firestore transaction
     * ```
     *
     * @param deque An [InstructionDeque] with instructions to execute.
     */
    abstract suspend operator fun invoke(deque: InstructionDeque)

}