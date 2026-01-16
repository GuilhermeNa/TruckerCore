package com.example.truckercore.layers.data.repository.instruction

import com.example.truckercore.layers.data.base.instruction._contracts.Instruction
import com.example.truckercore.layers.data.base.instruction.collections.InstructionDeque
import com.example.truckercore.layers.data.base.outcome.OperationOutcome

/**
 * Interface defining the repository responsible for managing and executing instructions.
 *
 * The repository is designed to accept different types of instructions and trigger their execution.
 * It serves as an abstraction layer for handling instructions, decoupling the logic of the instruction execution
 * from the actual persistence or data management mechanisms.
 *
 */
interface InstructionRepository {

    /**
     * Executes the provided [Instruction].
     *
     * This function will trigger the appropriate logic for processing and executing the instruction passed to it.
     * The implementation should define how the instruction is executed, whether it involves interacting with a
     * data source, a transaction, or some other mechanism.
     *
     * @param deque The [InstructionDeque] containing instructions to be executed.
     * @return [OperationOutcome] containing the operation result.
     */
    suspend operator fun invoke(deque: InstructionDeque): OperationOutcome

}