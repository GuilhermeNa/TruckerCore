package com.example.truckercore.modules.fleet.truck.entity

import com.example.truckercore.modules.fleet.shared.interfaces.Fleet
import com.example.truckercore.modules.fleet.truck.enums.TruckBrand
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

/**
 * Represents a truck entity in the system. A truck is associated with a business central and may have various attributes such as plate, color, brand, and audit data.
 * It implements both the `Entity` and `Fleet` interfaces.
 *
 * @property plate The truck's plate number. This is required and must follow the vehicle plate format.
 * @property color The truck's color. This is a required attribute and represents the truck's appearance.
 * @property brand The brand of the truck. This can provide insight into the truck's manufacturer (e.g., SCANIA, VOLVO).
 */
data class Truck(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    override val plate: String,
    override val color: String,
    val brand: TruckBrand
) : Entity, Fleet