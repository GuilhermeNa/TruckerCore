package com.example.truckercore.layers.data.base.instruction.for_app_impl

import com.example.truckercore.infra.instruction.base.Instruction
import com.example.truckercore.layers.data.repository.writer.exceptions.InstructionException

class InstructionDeque {

    private val _deque = ArrayDeque<Instruction>()

    fun addInstruction(vararg instruction: Instruction) {
        instruction.forEach {
            _deque.add(it)
        }
    }

    fun forEach(action: (Instruction) -> Unit) = _deque.forEach { action(it) }

    fun validate() {
        if (_deque.isEmpty()) throw InstructionException(
            "Instruction deque must not be empty. At least one instruction is required for execution."
        )

        if (hasDuplicates()) throw InstructionException(
            "Duplicate instructions found in the deque."
        )
    }

    private fun hasDuplicates() = _deque.size != _deque.distinct().size

}