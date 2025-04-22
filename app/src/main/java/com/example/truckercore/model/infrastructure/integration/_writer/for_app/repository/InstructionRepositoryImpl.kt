package com.example.truckercore.model.infrastructure.integration._writer.for_app.repository

import com.example.truckercore.model.infrastructure.integration._writer.for_api.InstructionExecutor
import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction

class InstructionRepositoryImpl(
    private val executor: InstructionExecutor<*>
): InstructionRepository {

    override fun invoke(inst: Instruction) {
        TODO("Not yet implemented")
    }

}