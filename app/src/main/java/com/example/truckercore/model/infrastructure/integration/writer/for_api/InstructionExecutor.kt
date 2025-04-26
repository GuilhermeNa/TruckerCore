package com.example.truckercore.model.infrastructure.integration.writer.for_api

import com.example.truckercore.model.infrastructure.integration.writer.for_api.exceptions.InstructionExecutorException
import com.example.truckercore.model.infrastructure.integration.writer.for_api.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction

/**
 * Abstract class responsible for orchestrating the execution of a sequence of [Instruction]s.
 *
 * This executor receives a queue of raw instructions, validates them, and delegates their transformation
 * to a domain-specific [InstructionInterpreter] for processing and execution (e.g., in a transactional context).
 *
 * Concrete implementations should define how instructions are executed (e.g., in Firestore, SQLite, etc.).
 *
 * @param I The specific type of [ApiInstruction] produced by the interpreter.
 * @property interpreter The interpreter responsible for transforming raw [Instruction]s into [ApiInstruction]s.
 */
abstract class InstructionExecutor<I : ApiInstruction>(
    protected val interpreter: InstructionInterpreter<I>
) {

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
     * @param deque A non-empty queue of validated instructions to execute.
     * @throws InvalidInstructionException if validation fails
     * @throws InstructionExecutorException for interpreter or execution errors
     */
    abstract suspend operator fun <T: Instruction>invoke(deque: ArrayDeque<T>)

}