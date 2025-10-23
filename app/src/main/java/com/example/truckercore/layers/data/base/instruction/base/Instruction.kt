package com.example.truckercore.layers.data.base.instruction.base

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

    fun getId(): String

    fun getCollection(): String

}