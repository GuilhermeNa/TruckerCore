package com.example.truckercore.modules.fleet.shared.module.licensing.entity

import com.example.truckercore.modules.fleet.shared.abstraction.FleetData
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

/**
 * Represents a licensing entity for fleet management, extending FleetData and implementing Entity.
 * This data class holds details about a license, including issuance and expiration dates, plate information,
 * and timestamps for creation and last modification.
 *
 * @property parentId The ID of the parent entity associated with the licensing record.
 * @property emissionDate Timestamp when the license was issued.
 * @property expirationDate Timestamp when the license expires.
 * @property plate Vehicle plate associated with the licensing record.
 * @property exercise Timestamp representing the exercise period of the license.
 */
data class Licensing(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    override val parentId: String,
    override val emissionDate: LocalDateTime,
    val expirationDate: LocalDateTime,
    val plate: String,
    val exercise: LocalDateTime
) : FleetData(parentId, emissionDate), Entity