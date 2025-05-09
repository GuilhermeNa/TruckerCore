package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.ApiInstructionExecutor
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.app_errors.ExecutorAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.shared.utils.sealeds.AppResult

class InstructionExecutorRepositoryImpl(
    private val executor: ApiInstructionExecutor,
    private val appErrorFactory: ExecutorAppErrorFactory
) : InstructionExecutorRepository {

    override suspend fun invoke(deque: InstructionDeque): AppResult<Unit> {
        return try {
            deque.validate()
            executor.invoke(deque)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(appErrorFactory(e))
        }
    }

}