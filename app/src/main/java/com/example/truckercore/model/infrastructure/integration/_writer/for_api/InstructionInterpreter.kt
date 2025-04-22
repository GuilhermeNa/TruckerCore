package com.example.truckercore.model.infrastructure.integration._writer.for_api

import com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction.Instruction

interface InstructionInterpreter<T : ApiInstruction> {

    operator fun invoke(deque: ArrayDeque<Instruction>): ArrayDeque<T>

}