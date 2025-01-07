package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.errors.NotApprovedObjectException

/**
 * The `Approvable` interface defines the structure for objects that require approval
 * from managers. Implementing this interface allows for tracking
 * and managing the approval status of the object.
 *
 * @param T The type of the object implementing the `Approvable` interface. This allows
 *          the interface to be used with different object types.
 */
interface Approvable<T> {

    /**
     * A read-only property that indicates whether the object has been approved.
     */
    val isApproved: Boolean

    /**
     * Marks the object as approved.
     * This method updates the approval status to `true`.
     *
     * @return The object itself (of type `T`), allowing for method chaining.
     */
    fun markAsApproved(): T

    /**
     * Marks the object as awaiting approval.
     * This method updates the approval status to `false`.
     *
     * @return The object itself (of type `T`), allowing for method chaining.
     */
    fun undoApproval(): T

    /**
     * Checks whether the object has been approved.
     *
     * @throws NotApprovedObjectException If the object is not approved.
     */
    fun validateApproval() {
        if (!this.isApproved) throw NotApprovedObjectException("The object has not been approved: $this")
    }

}