package com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.repository

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.shared.utils.sealeds.AppResult

/**
 * Interface defining the repository responsible for managing and executing instructions.
 *
 * The repository is designed to accept different types of instructions and trigger their execution.
 * It serves as an abstraction layer for handling instructions, decoupling the logic of the instruction execution
 * from the actual persistence or data management mechanisms.
 *
 */
interface InstructionExecutorRepository {

    /**
     * Executes the provided [Instruction].
     *
     * This function will trigger the appropriate logic for processing and executing the instruction passed to it.
     * The implementation should define how the instruction is executed, whether it involves interacting with a
     * data source, a transaction, or some other mechanism.
     *
     * ### Example usage:
     * ```kotlin
     * val instructions = InstructionDeque()
     * val result = executor(deque)
     * when(result) {
     *      is AppResult.Success -> // Operation succeed
     *      is AppResult.Error -> {
     *          val error = result.exception
     *          // handle error
     *      }
     * }
     *
     * // Executes all instructions inside a Firestore transaction
     * ```
     *
     * @param deque The [InstructionDeque] containing instructions to be executed.
     * @return [AppResult] containing the operation result.
     */
    suspend operator fun invoke(deque: InstructionDeque): AppResult<Unit>

}