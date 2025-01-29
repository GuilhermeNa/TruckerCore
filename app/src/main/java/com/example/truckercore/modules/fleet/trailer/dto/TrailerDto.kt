package com.example.truckercore.modules.fleet.trailer.dto

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.util.Date

data class TrailerDto(
    override val businessCentralId: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    val plate: String? = null,
    val color: String? = null,
    val brand: String? = null,
    val category: String? = null
) : Dto {

    override fun initializeId(newId: String): Dto = this.copy(
        id = newId,
        persistenceStatus = PersistenceStatus.PERSISTED.name
    )

}