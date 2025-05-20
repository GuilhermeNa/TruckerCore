package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.contracts.ApiInstructionInterpreter
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.contracts.ApiInstruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions.InterpreterException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction

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
abstract class ApiInstructionExecutor(
    protected val interpreter: ApiInstructionInterpreter
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
     * @param deque An [InstructionDeque] with instructions to execute.
     * @throws InvalidInstructionException if validation fails
     * @throws InterpreterException for interpreter or execution errors
     */
    abstract suspend operator fun invoke(deque: InstructionDeque)

}