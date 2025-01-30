package com.example.truckercore.modules.fleet.trailer.entity

import com.example.truckercore.modules.fleet.shared.abstraction.FleetData
import com.example.truckercore.modules.fleet.shared.interfaces.Fleet
import com.example.truckercore.modules.fleet.trailer.enums.TrailerBrand
import com.example.truckercore.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

data class Trailer(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    override val plate: String,
    override val color: String,
    override val documents: List<FleetData> = emptyList(),
    val brand: TrailerBrand,
    val category: TrailerCategory
): Entity, Fleet