package com.example.truckercore.infrastructure.database.firebase.errors

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.shared.interfaces.Dto

/**
 * Exception thrown when a task is unsuccessful, either due to issues with the provided DTO or an ID.
 *
 * This exception is used when a task related to Firebase (such as a database operation) fails. The task can
 * either fail due to an issue with a DTO or a missing ID. The exception allows capturing detailed information
 * about the failure through either a DTO or ID, and the collection involved in the operation.
 *
 * @param message A message that provides details about the reason for the failure.
 * @param dto The DTO associated with the task, if applicable.
 * @param id The ID associated with the task, if applicable.
 * @param collection The collection associated with the task that failed.
 */
class UnsuccessfulTaskException : FirebaseException {

    override var message: String = ""
    var dto: Dto? = null
    var id: String? = null
    var collection: Collection? = null

    /**
     * Constructor for creating an exception when the failure is related to a DTO.
     *
     * @param message The error message providing details about the failure.
     * @param dto The DTO that caused the failure.
     * @param collection The collection that was involved in the failed task.
     */
    constructor(message: String, dto: Dto, collection: Collection) : super() {
        this.message = message
        this.dto = dto
        this.collection = collection
    }

    /**
     * Constructor for creating an exception when the failure is related to an ID.
     *
     * @param message The error message providing details about the failure.
     * @param id The ID that caused the failure.
     * @param collection The collection that was involved in the failed task.
     */
    constructor(message: String, id: String, collection: Collection) : super() {
        this.message = message
        this.id = id
        this.collection = collection

    }

    constructor(message: String, collection: Collection) : super() {
        this.message = message
        this.collection = collection

    }

    /**
     * Returns detailed information about the task failure, either with the associated DTO or ID.
     *
     * @return A string representing either the DTO or ID associated with the failed task.
     */
    fun getTaskInfo(): String {
        return dto?.let { "Dto: $it" } ?: "Id: $id"
    }

}
