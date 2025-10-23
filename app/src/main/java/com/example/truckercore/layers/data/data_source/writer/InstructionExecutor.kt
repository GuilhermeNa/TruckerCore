package com.example.truckercore.layers.data.data_source.writer

import com.example.truckercore.layers.data.base.instruction.base.InstructionInterpreter
import com.example.truckercore.layers.data.base.instruction.for_app_impl.InstructionDeque

/**
 * Abstract class responsible for orchestrating the execution of a sequence of [Instruction]s.
 *
 * This executor receives a queue of raw instructions, validates them, and delegates their transformation
 * to a domain-specific [ApiInstructionInterpreter] for processing and execution (e.g., in a transactional context).
 *
 * Concrete implementations should define how instructions are executed (e.g., in Firestore, SQLite, etc.).
 *
 * @param I The specific type of [ApiInstruction] produced by the interpreter.
 * @property interpreter The interpreter responsible for transforming raw [Instruction]s into [ApiInstruction]s.
 */
abstract class InstructionExecutor(protected val interpreter: InstructionInterpreter) {

    /**
     * Executes the given queue of instructions.
     *
     * ### Example usage:
     * ```kotlin
     * val executor = FirestoreInstructionExecutor(firestore, FirestoreInstInterpreter(firestore))
     * val deque = ArrayDeque<Instruction>().apply {
     *     add(Put(...))
     *     add(Remove(...))
     * }
     * executor(deque) // Executes all instructions inside a Firestore transaction
     * ```
     *
     * @param deque An [InstructionDeque] with instructions to execute.
     * @throws InvalidInstructionException if validation fails
     * @throws InterpreterException for interpreter or execution errors
     */
    abstract suspend operator fun invoke(deque: InstructionDeque)

}