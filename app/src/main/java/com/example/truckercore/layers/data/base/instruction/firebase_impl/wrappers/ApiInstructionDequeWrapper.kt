package com.example.truckercore.layers.data.base.instruction.firebase_impl.wrappers

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.instruction.base.ApiInstructionWrapper

class ApiInstructionDequeWrapper {

    private val _deque = ArrayDeque<ApiInstructionWrapper>()

    fun add(apiInstruction: ApiInstructionWrapper) {
        _deque.add(apiInstruction)
    }

    fun validate() {
        if (_deque.isEmpty()) throw InfraException.Instruction(
            "API Instruction deque must not be empty. At least one instruction is required for execution."
        )

        if (hasDuplicates()) throw InfraException.Instruction(
            "Duplicate api instructions found in the deque."
        )
    }

    fun forEach(action: (ApiInstructionWrapper) -> Unit) {
        _deque.forEach { action(it) }
    }

    private fun hasDuplicates() = _deque.size != _deque.distinct().size

}