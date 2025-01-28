package com.example.truckercore.shared.modules.storage_file.entity

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.net.URL
import java.time.LocalDateTime

/**
 * Represents a file stored in Firebase Storage, containing metadata and status information.
 * This class is used to map the file's data between the database and its Data Transfer Object (DTO) representation.
 * @property parentId The identifier of the parent object to which this storage file belongs.
 * This establishes a relationship between the file and its associated object.
 * @property url The URL pointing to the location of the file in Firebase Storage.
 * @property isUpdating A flag indicating whether the file is currently being updated in Firebase Storage.
 * Defaults to `false`.
 *
 * @throws IllegalArgumentException if the URL provided is not valid.
 */
data class StorageFile(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val parentId: String,
    val url: String,
    val isUpdating: Boolean
) : Entity {

    init {
        URL(url) // Validate if the URL is well-formed when provided.
    }

}
