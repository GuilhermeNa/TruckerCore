package com.example.truckercore.model.infrastructure.integration._instruction.for_api

import com.example.truckercore.model.infrastructure.integration._instruction.for_app.Instruction

interface InstructionExecutor {

     suspend operator fun invoke(deque: ArrayDeque<Instruction>)

}