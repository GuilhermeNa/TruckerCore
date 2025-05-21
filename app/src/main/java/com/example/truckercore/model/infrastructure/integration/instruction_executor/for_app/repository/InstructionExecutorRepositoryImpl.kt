package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository

import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.ApiInstructionExecutor
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.logger.AppLogger

class InstructionExecutorRepositoryImpl(
    private val executor: ApiInstructionExecutor,
    private val errorFactory: InstructionRepositoryErrorFactory // TODO(injetar este factory)
) : InstructionExecutorRepository {

    companion object {
        private const val SUCCESS_MESSAGE = "Instruction executed successfully: "
        private const val ERROR_MESSAGE = "Failed to execute instruction: "
    }

    override suspend fun invoke(deque: InstructionDeque): AppResult<Unit> {
        return try {
            deque.validate()
            executor.invoke(deque)
            AppLogger.i(getClassName(), "$SUCCESS_MESSAGE $deque")
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppLogger.e(getClassName(), "$ERROR_MESSAGE $deque")
            AppResult.Error(errorFactory(e))
        }
    }

}