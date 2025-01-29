package com.example.truckercore.modules.fleet.truck.entity

import com.example.truckercore.modules.fleet.shared.interfaces.Fleet
import com.example.truckercore.modules.fleet.truck.enums.TruckBrand
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

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