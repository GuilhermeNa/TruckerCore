package com.example.truckercore.layers.data.base.instruction.collections

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.instruction._contracts.ApiInstructionWrapper

/**
 * A wrapper around an [ArrayDeque] that manages a sequence of [ApiInstructionWrapper]s.
 *
 * This class is responsible for:
 * - Storing API-specific instructions in insertion order
 * - Providing controlled iteration over executable instructions
 * - Validating execution preconditions (non-empty and no duplicates)
 *
 * It acts as an infrastructure-level container that ensures API instructions
 * are valid and safe to execute before being processed by an executor.
 */
class ApiInstructionDeque {

    // Internal deque that stores API instructions in FIFO order
    private val _deque = ArrayDeque<ApiInstructionWrapper>()

    /**
     * Adds a single [ApiInstructionWrapper] to the deque.
     *
     * Instructions are appended in insertion order and will be executed sequentially.
     *
     * @param apiInstruction API-specific instruction to be added.
     */
    fun add(apiInstruction: ApiInstructionWrapper) {
        _deque.add(apiInstruction)
    }

    /**
     * Validates the current state of the API instruction deque.
     *
     * Validation rules:
     * - The deque must not be empty
     * - The deque must not contain duplicate API instructions
     *
     * @throws InfraException.Instruction if validation fails.
     */
    fun validate() {

        // Ensures that at least one API instruction exists before execution
        if (_deque.isEmpty()) throw InfraException.Instruction(
            "API Instruction deque must not be empty. At least one instruction is required for execution."
        )

        // Ensures that no duplicate API instructions are present
        if (hasDuplicates()) throw InfraException.Instruction(
            "Duplicate API instructions found in the deque."
        )
    }

    /**
     * Iterates over each API instruction in the deque and applies the given action.
     *
     * @param action A lambda function to be executed for each [ApiInstructionWrapper].
     */
    fun forEach(action: (ApiInstructionWrapper) -> Unit) {
        _deque.forEach { apiInstruction -> action(apiInstruction) }
    }

    /**
     * Checks whether the deque contains duplicate API instructions.
     *
     * @return true if duplicates are found, false otherwise.
     */
    private fun hasDuplicates(): Boolean = _deque.size != _deque.distinct().size

}