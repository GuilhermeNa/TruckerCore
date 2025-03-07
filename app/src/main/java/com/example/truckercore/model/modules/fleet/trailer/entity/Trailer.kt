package com.example.truckercore.model.modules.fleet.trailer.entity

import com.example.truckercore.modules.fleet.shared.interfaces.Fleet
import com.example.truckercore.modules.fleet.trailer.enums.TrailerBrand
import com.example.truckercore.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

/**
 * Represents a trailer entity in the system. A trailer is associated with a business central and can be linked to a truck.
 * It contains various attributes such as its identification plate, color, brand, and category.
 *
 * @property brand The brand of the trailer. This can provide insights into the manufacturer.
 * @property category The category of the trailer (e.g., flatbed, enclosed).
 * @property truckId The ID of the truck associated with this trailer, if applicable. This can be null if the trailer is not attached to a truck.
 */
data class Trailer(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    override val plate: String,
    override val color: String,
    val brand: TrailerBrand,
    val category: TrailerCategory,
    val truckId: String? = null
): Entity, Fleet