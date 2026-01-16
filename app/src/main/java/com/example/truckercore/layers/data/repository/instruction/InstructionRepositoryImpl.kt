package com.example.truckercore.layers.data.repository.instruction

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
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

    private suspend fun runSafeOperation(block: suspend () -> Unit): OperationOutcome =
        try {
            block()
            OperationOutcome.Completed
        } catch (e: AppException) {
            OperationOutcome.Failure(e)
        } catch (e: Exception) {
            OperationOutcome.Failure(
                DataException.Unknown(
                    message = "An unknown error occurred in Instruction Repository.",
                    cause = e
                )
            )
        }


}