package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.ApiInstructionExecutor
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.app_errors.ExecutorAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore._utils.classes.AppResult

class InstructionExecutorRepositoryImpl(
    private val executor: ApiInstructionExecutor,
    private val appErrorFactory: ExecutorAppErrorFactory
) : InstructionExecutorRepository {

    companion object {
        private const val ERROR_MESSAGE = "Failed to execute instructions in the deque."
    }

    override suspend fun invoke(deque: InstructionDeque): AppResult<Unit> {
        return try {
            deque.validate()
            executor.invoke(deque)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            appErrorFactory("$ERROR_MESSAGE $deque", e)
        }
    }

}