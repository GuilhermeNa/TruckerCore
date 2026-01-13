package com.example.truckercore.layers.data.data_source.instruction

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.instruction._contracts.ApiInstructionWrapper
import com.example.truckercore.layers.data.base.instruction._contracts.Instruction
import com.example.truckercore.layers.data.base.instruction._contracts.InstructionInterpreter
import com.example.truckercore.layers.data.base.instruction.collections.ApiInstructionDeque
import com.example.truckercore.layers.data.base.instruction.api_instructions.DeleteWrapper
import com.example.truckercore.layers.data.base.instruction.api_instructions.SetWrapper
import com.example.truckercore.layers.data.base.instruction.collections.InstructionDeque
import com.example.truckercore.layers.data.base.instruction.instructions.Put
import com.example.truckercore.layers.data.base.instruction.instructions.Remove
import com.example.truckercore.layers.data.base.instruction.instructions.Update
import com.google.firebase.firestore.FirebaseFirestore

class InstructionInterpreterImpl(
    private val firestore: FirebaseFirestore
) : InstructionInterpreter {

    override fun invoke(deque: InstructionDeque): ApiInstructionDeque {
        // Creates an empty deque that will store Firebase-specific instructions
        val apiDeque = ApiInstructionDeque()

        // Iterates over each generic instruction in the input deque
        deque.forEach { instruction ->
            // Interprets the current instruction and converts it to an API instruction
            val apiInstruction = interpret(instruction)

            // Adds the converted instruction to the API instruction deque
            apiDeque.add(apiInstruction)
        }

        // Returns the deque containing all interpreted Firebase instructions
        return apiDeque
    }

    private fun interpret(instruction: Instruction): ApiInstructionWrapper {
        // Determines the concrete type of the instruction at runtime
        return when (instruction) {
            is Put -> getFirebaseSetByPut(instruction)
            is Remove -> getFirebaseDelete(instruction)
            is Update -> getFirebaseSetByUpdate(instruction)
            else -> throw InfraException.Instruction(
                "Unsupported instruction type: ${instruction::class.simpleName}"
            )
        }
    }

    private fun getFirebaseSetByPut(instruction: Put) = with(instruction) {
        // Retrieves the Firestore collection using the name provided by the instruction
        val document = firestore
            .collection(getCollection())
            .document(getId())

        // Wraps the document reference and data into a Firestore set instruction
        SetWrapper(document, instruction.data)
    }

    private fun getFirebaseDelete(instruction: Remove) = with(instruction) {
        // Retrieves the Firestore collection using the name provided by the instruction
        val document = firestore
            .collection(getCollection())
            .document(getId())

        // Wraps the document reference into a Firestore delete instruction
        DeleteWrapper(document)
    }

    private fun getFirebaseSetByUpdate(instruction: Update) = with(instruction) {
        // Retrieves the Firestore collection using the name provided by the instruction
        val document = firestore
            .collection(getCollection())
            .document(getId())

        // Wraps the document reference and updated data into a Firestore set instruction
        SetWrapper(document, instruction.data)
    }

}