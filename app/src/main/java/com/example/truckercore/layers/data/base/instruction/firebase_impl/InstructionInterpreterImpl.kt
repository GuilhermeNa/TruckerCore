package com.example.truckercore.layers.data.base.instruction.firebase_impl

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.infra.instruction.base.ApiInstructionWrapper
import com.example.truckercore.infra.instruction.base.Instruction
import com.example.truckercore.infra.instruction.firebase_impl.wrappers.ApiInstructionDequeWrapper
import com.example.truckercore.layers.data.base.instruction.firebase_impl.wrappers.DeleteWrapper
import com.example.truckercore.infra.instruction.firebase_impl.wrappers.SetWrapper
import com.example.truckercore.infra.instruction.for_app_impl.Put
import com.example.truckercore.infra.instruction.for_app_impl.Remove
import com.example.truckercore.infra.instruction.for_app_impl.Update
import com.google.firebase.firestore.FirebaseFirestore

class InstructionInterpreterImpl(
    private val firestore: FirebaseFirestore
) : com.example.truckercore.layers.data.base.instruction.base.InstructionInterpreter {

    override fun invoke(deque: com.example.truckercore.layers.data.base.instruction.for_app_impl.InstructionDeque): ApiInstructionDequeWrapper {
        val apiDeque = ApiInstructionDequeWrapper()

        deque.forEach { instruction ->
            val apiInstruction = interpret(instruction)
            apiDeque.add(apiInstruction)
        }

        return apiDeque
    }

    private fun interpret(instruction: Instruction): ApiInstructionWrapper {
        return when (instruction) {
            is Put -> getFirebaseSetByPut(instruction)
            is Remove -> getFirebaseDelete(instruction)
            is Update -> getFirebaseSetByUpdate(instruction)
            else -> throw InfraException.Instruction(
                "Unsupported instruction type: ${instruction::class.simpleName}"
            )
        }
    }

    private fun getFirebaseSetByPut(instruction: Put): SetWrapper = with(instruction) {
        val document = firestore.collection(getCollection()).document(getId())
        SetWrapper(
            document = document,
            data = instruction.data
        )
    }

    private fun getFirebaseDelete(instruction: Remove): com.example.truckercore.layers.data.base.instruction.firebase_impl.wrappers.DeleteWrapper =
        with(instruction) {
            val document = firestore.collection(getCollection()).document(getId())
            com.example.truckercore.layer.data.core.instruction.firebase_impl.wrappers.DeleteWrapper(
                document
            )
        }

    private fun getFirebaseSetByUpdate(instruction: Update): SetWrapper =
        with(instruction) {
            val document = firestore.collection(getCollection()).document(getId())
            SetWrapper(
                document = document,
                data = instruction.data
            )
        }

}