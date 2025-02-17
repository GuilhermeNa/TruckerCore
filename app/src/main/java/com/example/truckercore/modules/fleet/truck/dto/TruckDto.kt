package com.example.truckercore.modules.fleet.truck.dto

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.util.Date

data class TruckDto(
    override val businessCentralId: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    val plate: String? = null,
    val color: String? = null,
    val brand: String? = null
) : Dto {

    override fun initializeId(newId: String): TruckDto = this.copy(
        id = newId,
        persistenceStatus = PersistenceStatus.PERSISTED.name
    )

}