package com.example.truckercore.model.infrastructure.integration._writer.for_api

import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction

abstract class InstructionExecutor<T : ApiInstruction>(
    protected val interpreter: InstructionInterpreter<T>
) {

    abstract suspend operator fun invoke(deque: ArrayDeque<Instruction>)

}