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
     * Checks whether the identifier (id) has been initialized and is valid.
     * An identifier is considered valid if it is not null or empty.
     *
     * @return true if the id is not null or empty, indicating the id is valid; false otherwise.
     */
    fun hasValidId() = !id.isNullOrEmpty()

}