package com.example.truckercore.layers.data.base.instruction.collections

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.instruction._contracts.Instruction
import com.example.truckercore.layers.data.base.instruction.abstraction.InstructionExecutor

/**
 * A wrapper around an [ArrayDeque] that manages a sequence of [Instruction]s.
 *
 * This class is responsible for:
 * - Storing instructions in insertion order
 * - Providing iteration over instructions
 * - Validating execution preconditions (non-empty and no duplicates)
 *
 * It acts as a domain-level container to ensure instruction integrity
 * before execution by an [InstructionExecutor].
 */
class InstructionDeque {

    // Internal deque that stores the instructions in FIFO order
    private val _deque = ArrayDeque<Instruction>()

    /**
     * Adds one or more [Instruction]s to the deque.
     *
     * @param instruction Vararg list of instructions to be added.
     */
    fun addInstruction(vararg instruction: Instruction) {

        // Iterates over each provided instruction
        instruction.forEach {

            // Adds the instruction to the internal deque
            _deque.add(it)
        }
    }

    /**
     * Iterates over each instruction in the deque and applies the given action.
     *
     * @param action A lambda function to be executed for each [Instruction].
     */
    fun forEach(action: (Instruction) -> Unit) =
        _deque.forEach { action(it) }

    /**
     * Validates the current state of the instruction deque.
     *
     * Validation rules:
     * - The deque must not be empty
     * - The deque must not contain duplicate instructions
     *
     * @throws InfraException.Instruction if validation fails.
     */
    fun validate() {

        // Ensures that at least one instruction exists before execution
        if (_deque.isEmpty()) throw InfraException.Instruction(
            "Instruction deque must not be empty. At least one instruction is required for execution."
        )

        // Ensures that no duplicate instructions are present
        if (hasDuplicates()) throw InfraException.Instruction(
            "Duplicate instructions found in the deque."
        )
    }

    /**
     * Checks whether the deque contains duplicate instructions.
     *
     * @return true if duplicates are found, false otherwise.
     */
    private fun hasDuplicates(): Boolean =
        _deque.size != _deque.distinct().size

}