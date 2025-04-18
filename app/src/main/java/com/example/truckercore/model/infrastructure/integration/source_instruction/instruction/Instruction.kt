package com.example.truckercore.model.infrastructure.integration.source_instruction.instruction

import com.example.truckercore.model.configs.constants.Collection

interface Instruction {
    val instructionTag: InstructionTag
    val collection: Collection
    val collectionName get() = collection.name
}