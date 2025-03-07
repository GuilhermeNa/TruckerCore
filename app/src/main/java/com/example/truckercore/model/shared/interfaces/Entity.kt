package com.example.truckercore.model.shared.interfaces

import com.example.truckercore.model.shared.enums.PersistenceStatus
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

}