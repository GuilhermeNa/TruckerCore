package com.example.truckercore.layers.data.repository.writer

import com.example.truckercore.layers.data.base.instruction.collections.InstructionDeque
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.base.instruction.abstraction.InstructionExecutor

class InstructionRepositoryImpl(
    private val executor: InstructionExecutor
) : InstructionRepository {

    override suspend fun invoke(deque: InstructionDeque): OperationOutcome =
        runSafeOperation {
            deque.validate()
            executor.invoke(deque)
        }

}