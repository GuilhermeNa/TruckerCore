package com.example.truckercore.model.infrastructure.integration.instruction

interface InstructionExecutor {

     suspend operator fun invoke(deque: ArrayDeque<Instruction>)

}