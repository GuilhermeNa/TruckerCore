package com.example.truckercore.model.infrastructure.integration.writer.for_app.repository

import com.example.truckercore.model.infrastructure.integration.writer.for_api.InstructionExecutor
import com.example.truckercore.model.infrastructure.integration.writer.for_app.app_errors.ExecutorAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction.Instruction
import com.example.truckercore.model.shared.utils.sealeds.AppResult

class ExecutorRepositoryImpl(
    private val executor: InstructionExecutor<*>,
    private val appErrorFactory: ExecutorAppErrorFactory
) : ExecutorRepository {

    override suspend fun <T : Instruction> invoke(instructions: ArrayDeque<T>): AppResult<Unit> {
        return try {
            executor.invoke(instructions)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(appErrorFactory(e))
        }
    }

}