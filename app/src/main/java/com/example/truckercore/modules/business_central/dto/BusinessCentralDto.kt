package com.example.truckercore.modules.business_central.dto

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.util.Date

internal data class BusinessCentralDto(
    override val businessCentralId: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null
) : Dto {

    override fun initializeId(newId: String): BusinessCentralDto {
        return this.copy(id = newId, persistenceStatus = PersistenceStatus.PERSISTED.name)
    }

}