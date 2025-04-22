package com.example.truckercore.model.infrastructure.integration._writer.for_app.instruction

import com.example.truckercore.model.configs.constants.Collection

interface Instruction {
    val instructionTag: InstructionTag
    val collection: Collection
    val collectionName get() = collection.name
}