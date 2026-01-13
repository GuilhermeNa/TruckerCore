package com.example.truckercore.layers.data.base.instruction._contracts

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction

/**
 * Represents an executable Firestore-specific instruction within a transaction context.
 *
 * This interface extends [ApiInstructionWrapper] and includes:
 * - A [document] reference to operate on
 * - An [execute] function that applies the operation to the given Firestore [Transaction]
 *
 * All concrete implementations of this interface encapsulate Firestore operations
 * such as insertions, deletions, and updates.
 */
interface Transactional {

    /**
     * The Firestore document reference this instruction targets.
     */
    val document: DocumentReference

    /**
     * Executes this instruction within a Firestore [Transaction].
     *
     * This method is called within the transactional block of a Firestore operation.
     * The actual logic depends on the specific type of instruction (e.g. delete, set, update).
     *
     * ### Example usage:
     * ```kotlin
     * val instruction: FirebaseInstruction = FirebaseSet(
     *     instructionTag = InstructionTag("user_create"),
     *     document = firestore.collection("users").document(),
     *     data = UserDto("Alice", email = "alice@example.com")
     * )
     *
     * firestore.runTransaction { transaction ->
     *     instruction.execute(transaction)
     * }
     * ```
     *
     * @param transaction The Firestore transaction in which the operation should be performed.
     */
    fun execute(transaction: Transaction)

}