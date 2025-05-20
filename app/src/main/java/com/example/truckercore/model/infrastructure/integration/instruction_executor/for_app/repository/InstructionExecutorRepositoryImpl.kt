package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.expressions.getName
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.ApiInstructionExecutor
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.logger.AppLogger

class InstructionExecutorRepositoryImpl(
    private val executor: ApiInstructionExecutor,
    private val errorFactory: InstructionExecutorErrorFactory
) : InstructionExecutorRepository {

    companion object {
        private const val SUCCESS_MESSAGE = "Instruction executed successfully: "
        private const val ERROR_MESSAGE = "Failed to execute instruction: "
    }

    override suspend fun invoke(deque: InstructionDeque): AppResult<Unit> {
        return try {
            deque.validate()
            executor.invoke(deque)
            AppLogger.i(getName(), "$SUCCESS_MESSAGE $deque")
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppLogger.e(getName(), "$ERROR_MESSAGE $deque")
            val appError = errorFactory(e)
            AppResult.Error(appError)
        }
    }

}