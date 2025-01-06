package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.annotations.Required
import java.util.Date

/**
 * A generic interface that represents a Data Transfer Object (DTO).
 * DTOs are used for transferring data between different layers of an application.
 *
 * @param T The type of the model associated with this DTO. This type is used for converting
 *          the DTO to the model and ensuring that the necessary fields are present.
 */
interface Dto<T> {
    val masterUid: String?
    val id: String?
    val lastModifierId: String?
    val creationDate: Date?
    val lastUpdate: Date?
    val persistenceStatus: String?

    /**
     * Initializes the `id` of the DTO with the provided new ID. This method returns a new
     * instance of the DTO with the updated ID.
     *
     * @param newId The new ID to assign to the DTO.
     * @return A new instance of the DTO with the updated ID.
     */
    fun initializeId(newId: String): Dto<T>

}