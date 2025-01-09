package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime

/**
 * It establishes a contract for various model classes within the system.
 *
 */
interface Entity {

    /**
     * The unique identifier for the master entity associated with this MODEL.
     */
    val businessCentralId: String

    /**
     * The unique identifier for this MODEL. Can be null before dataBase insertion.
     */
    val id: String?

    /**
     * Represents the ID of the user who last modified the object.
     */
    val lastModifierId: String

    /**
     * The creation date of this MODEL.
     */
    val creationDate: LocalDateTime

    /**
     * The last update date of this MODEL.
     */
    val lastUpdate: LocalDateTime

    /**
     * A flag indicating whether the object has been persisted (saved) to the database.
     */
    val persistenceStatus: PersistenceStatus

    /**
     * Checks whether the id has been initialized.
     *
     * @throws UninitializedPropertyAccessException If the object is not approved.
     */
    fun validateId() {
        if (id == null) throw UninitializedPropertyAccessException("ID has not been initialized.")
    }

    /**
     * Validates that the object has been persisted in the database.
     *
     * @throws InvalidStateException If the `persistenceStatus` is not `PERSISTED`,
     *         indicating that the object has not been properly saved in the database.
     */
    fun validatePersisted() {
        PersistenceStatus.validateState(
            actualStatus = persistenceStatus,
            validStatuses = arrayOf(PersistenceStatus.PERSISTED)
        )
    }

}