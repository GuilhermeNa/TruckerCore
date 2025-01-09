package com.example.truckercore.shared.interfaces

import java.util.Date

/**
 * A generic interface that represents a Data Transfer Object (DTO).
 * DTOs are used for transferring data between different layers of an application.
 */
interface Dto {
    val businessCentralId: String?
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
    fun initializeId(newId: String): Dto

}