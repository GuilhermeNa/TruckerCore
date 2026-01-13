package com.example.truckercore.layers.data.data_source.instruction

import com.example.truckercore.layers.data.base.instruction._contracts.InstructionInterpreter
import com.example.truckercore.layers.data.base.instruction._contracts.Transactional
import com.example.truckercore.layers.data.base.instruction.abstraction.InstructionExecutor
import com.example.truckercore.layers.data.base.instruction.collections.InstructionDeque
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class InstructionExecutorImpl(
    interpreter: InstructionInterpreter,
    private val firestore: FirebaseFirestore
) : InstructionExecutor(interpreter) {

    override suspend fun invoke(deque: InstructionDeque) {
        // Starts a Firestore transaction
        firestore.runTransaction { transaction ->

            // Transforms the raw instruction deque into API-specific instructions
            val apiInstructionDeque = interpreter(deque)

            // Validates the transformed instructions before execution
            apiInstructionDeque.validate()

            // Iterates over each instruction in the deque executing them in firebase context
            apiInstructionDeque.forEach {
                (it as Transactional).execute(transaction)
            }

        }.await()
    }

}



