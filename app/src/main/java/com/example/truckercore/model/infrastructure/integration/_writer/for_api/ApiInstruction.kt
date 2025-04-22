package com.example.truckercore.model.infrastructure.integration._writer.for_api

import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.InstructionTag

interface ApiInstruction {
    val instructionTag: InstructionTag
}