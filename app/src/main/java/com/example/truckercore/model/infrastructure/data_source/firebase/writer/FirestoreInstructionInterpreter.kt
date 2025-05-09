package com.example.truckercore.model.infrastructure.data_source.firebase.writer

import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseDelete
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseSet
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.contracts.ApiInstructionInterpreter
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.collections.ApiInstructionQueue
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.data.contracts.ApiInstruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions.InstructionInterpreterException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.collections.InstructionDeque
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.contracts.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Put
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Remove
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Update
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreInstructionInterpreter(
    private val firestore: FirebaseFirestore
) : ApiInstructionInterpreter {

    override fun invoke(deque: InstructionDeque): ApiInstructionQueue {
        val apiDeque = ApiInstructionQueue()

        deque.forEach { instruction ->
            val apiInstruction = interpret(instruction)
            apiDeque.add(apiInstruction)
        }

        return apiDeque
    }

    private fun interpret(instruction: Instruction): ApiInstruction {
        return when (instruction) {
            is Put -> getFirebaseSetByPut(instruction)
            is Remove -> getFirebaseDelete(instruction)
            is Update -> getFirebaseSetByUpdate(instruction)
            else -> throw InstructionInterpreterException(
                "Unsupported instruction type: ${instruction::class.simpleName}"
            )
        }
    }

    private fun getFirebaseSetByPut(instruction: Put): FirebaseSet = with(instruction) {
        val document = firestore.collection(getCollection()).document(getId())
        FirebaseSet(
            document = document,
            data = instruction.data
        )
    }

    private fun getFirebaseDelete(instruction: Remove): FirebaseDelete = with(instruction) {
        val document = firestore.collection(getCollection()).document(getId())
        FirebaseDelete(document)
    }

    private fun getFirebaseSetByUpdate(instruction: Update): FirebaseSet = with(instruction) {
        val document = firestore.collection(getCollection()).document(getId())
        FirebaseSet(
            document = document,
            data = instruction.data
        )
    }

}