package com.example.truckercore.model.infrastructure.integration.source_instruction

import com.example.truckercore.model.infrastructure.integration.source_instruction.instruction.Instruction

interface InstructionExecutor {

     suspend operator fun invoke(deque: ArrayDeque<Instruction>)

}