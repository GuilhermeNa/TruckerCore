package com.example.truckercore.model.infrastructure.data_source.firebase.writer

import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.contracts.Transactional
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.ApiInstructionExecutor
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.contracts.ApiInstructionInterpreter
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.collections.ApiInstructionQueue
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.logger.AppLogger
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreInstructionExecutor(
    interpreter: ApiInstructionInterpreter,
    private val firestore: FirebaseFirestore
) : ApiInstructionExecutor(interpreter) {

    override suspend fun invoke(deque: InstructionDeque) {
        firestore.runTransaction { transaction ->
            val fbInstructions = interpreter(deque)

            fbInstructions.validate()

            fbInstructions.forEach {
                (it as Transactional).execute(transaction)
            }

            logTransactionSuccess(fbInstructions)

        }.await()
    }

    private fun logTransactionSuccess(instructions: ApiInstructionQueue) {
        AppLogger.i(
            getClassName(),
            "$SUCCESS_MSG $instructions."
        )
    }

    companion object {
        private const val SUCCESS_MSG =
            "Firestore Transaction: Successfully executed instruction(s):"
    }

}



