package com.example.truckercore.model.infrastructure.integration.writer.for_app.instruction

import com.example.truckercore.model.configs.constants.Collection

/**
 * Interface representing an instruction within the system, typically used in workflows or processing pipelines.
 *
 * This interface serves as a contract for all types of instructions, providing essential properties
 * such as an instruction tag, associated collection, and the collection name.
 * It can be extended by specific instruction types that encapsulate the logic or data of the operation
 * being performed (e.g., put, update, remove).
 *
 * The [instructionTag] uniquely identifies the instruction, ensuring proper tracking and execution sequencing,
 * while [collection] refers to the data structure or entity on which the operation is to be performed.
 */
interface Instruction {

    /**
     * A unique tag used to identify the instruction, enabling dependency management, sequencing,
     * and tracking of operations.
     */
    val instructionTag: InstructionTag

    /**
     * The collection to which this instruction applies. It represents the target data entity
     * or storage unit that the instruction is operating on.
     */
    val collection: Collection
    val collectionName: String get() = collection.name

}