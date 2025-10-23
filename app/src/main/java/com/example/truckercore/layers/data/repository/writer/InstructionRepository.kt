package com.example.truckercore.layers.data.repository.writer

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.instruction.for_app_impl.InstructionDeque
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
    suspend operator fun invoke(deque: com.example.truckercore.layers.data.base.instruction.for_app_impl.InstructionDeque): OperationOutcome

    suspend fun runSafeOperation(block: suspend () -> Unit): OperationOutcome =
        try {
            block()
            OperationOutcome.Completed
        } catch (e: AppException) {
            OperationOutcome.Failure(e)
        } catch (e: Exception) {
            OperationOutcome.Failure(
                DataException.Unknown(
                    message = "An unknown error occurred in Instruction Repository.",
                    cause = e
                )
            )
        }

}