package com.example.truckercore.model.modules.business_central.entity

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

/**
 * Represents a Business Central in the system, which serves as the central entity for business-related data.
 * This class contains the core information about the business, such as its unique identifier, creation and modification dates,
 * and other essential business-related attributes.
 *
 * The `BusinessCentral` entity plays a critical role in the system, acting as the representation of a business unit or entity
 * within the system, encompassing key business-specific information.
 * @param keys The number of VIP keys associated with the business central. Each key represents a unique access
 *             granted for the registration of new users who will be able to manage the business data.
 */
data class BusinessCentral(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val keys: Int
) : Entity