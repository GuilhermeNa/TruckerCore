package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.exceptions.InvalidInstructionException

class InstructionDeque {

    private val _deque = ArrayDeque<Instruction>()

    fun addInstruction(vararg instruction: Instruction) {
        instruction.forEach {
            _deque.add(it)
        }
    }

    fun forEach(action: (Instruction) -> Unit) = _deque.forEach { action(it) }

    fun validate() {
        if (_deque.isEmpty()) throw InvalidInstructionException(
            "Instruction deque must not be empty. At least one instruction is required for execution."
        )

        if (hasDuplicates()) throw InvalidInstructionException(
            "Duplicate instructions found in the deque."
        )
    }

    private fun hasDuplicates() = _deque.size != _deque.distinct().size

}