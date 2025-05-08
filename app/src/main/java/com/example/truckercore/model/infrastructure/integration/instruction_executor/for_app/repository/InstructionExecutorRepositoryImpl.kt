package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.InstructionExecutor
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.app_errors.ExecutorAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.Instruction
import com.example.truckercore.model.shared.utils.sealeds.AppResult

class InstructionExecutorRepositoryImpl(
    private val executor: InstructionExecutor<*>,
    private val appErrorFactory: ExecutorAppErrorFactory
) : InstructionExecutorRepository {

    override suspend fun <T : Instruction> invoke(instructions: ArrayDeque<T>): AppResult<Unit> {
        return try {
            executor.invoke(instructions)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(appErrorFactory(e))
        }
    }

}