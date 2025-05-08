package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag

/**
 * Interface representing a high-level API instruction that can be interpreted and executed on the backend.
 *
 * This interface defines instructions that are part of the business logic layer, which are later
 * transformed into specific backend instructions (e.g. [FirebaseInstruction]) for execution.
 *
 * Each instruction has a unique [instructionTag], which is used to identify the operation and handle
 * dependencies between instructions in complex workflows or batch operations.
 */
interface ApiInstruction {

    /**
     * A unique tag used to identify this instruction, allowing dependency management and traceability
     * when the instruction is transformed into a backend-specific format.
     */
    val instructionTag: InstructionTag

}