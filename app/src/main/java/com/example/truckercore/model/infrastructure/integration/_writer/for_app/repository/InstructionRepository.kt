package com.example.truckercore.model.infrastructure.integration._writer.for_app.repository

import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction

interface InstructionRepository {

    operator fun invoke(inst: Instruction)

}