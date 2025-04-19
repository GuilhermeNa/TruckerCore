package com.example.truckercore.model.infrastructure.integration._instruction.for_app

import com.example.truckercore.model.configs.constants.Collection

interface Instruction {
    val instructionTag: InstructionTag
    val collection: Collection
    val collectionName get() = collection.name
}