package com.example.truckercore.layers.data.data_source.writer

import com.example.truckercore.layers.data.base.instruction.base.InstructionInterpreter
import com.example.truckercore.layers.data.base.instruction.firebase_impl.base.Transactional
import com.example.truckercore.layers.data.base.instruction.for_app_impl.InstructionDeque
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class InstructionExecutorImpl(
    interpreter: InstructionInterpreter,
    private val firestore: FirebaseFirestore
) : InstructionExecutor(interpreter) {

    override suspend fun invoke(deque: InstructionDeque) {
        firestore.runTransaction { transaction ->
            val apiInstructionDeque = interpreter(deque)

            apiInstructionDeque.validate()

            apiInstructionDeque.forEach {
                (it as Transactional).execute(transaction)
            }

        }.await()
    }

}



