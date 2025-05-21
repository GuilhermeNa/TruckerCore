package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.collections

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.contracts.ApiInstruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions.ApiInstructionException

class ApiInstructionQueue {

    private val _deque = ArrayDeque<ApiInstruction>()

    fun add(apiInstruction: ApiInstruction) {
        _deque.add(apiInstruction)
    }

    fun validate() {
        if (_deque.isEmpty()) throw ApiInstructionException(
            "API Instruction deque must not be empty. At least one instruction is required for execution."
        )

        if (hasDuplicates()) throw ApiInstructionException(
            "Duplicate api instructions found in the deque."
        )
    }

    fun forEach(action: (ApiInstruction) -> Unit) {
        _deque.forEach { action(it) }
    }

    private fun hasDuplicates() = _deque.size != _deque.distinct().size

}