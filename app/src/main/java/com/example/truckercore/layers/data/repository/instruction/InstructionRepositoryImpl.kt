package com.example.truckercore.layers.data.repository.instruction

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.instruction.abstraction.InstructionExecutor
import com.example.truckercore.layers.data.base.instruction.collections.InstructionDeque
import com.example.truckercore.layers.data.base.outcome.OperationOutcome

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
            val appError = DataException.Unknown(UNKNOWN_ERROR, e)
            OperationOutcome.Failure(appError)
        }

    private companion object {
        private const val UNKNOWN_ERROR = "An unknown error occurred in Instruction Repository."
    }

}