package com.example.truckercore.modules.central.dto

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.util.Date

internal data class CentralDto(
    override val centralId: String?,
    override val id: String?,
    override val lastModifierId: String?,
    override val creationDate: Date?,
    override val lastUpdate: Date?,
    override val persistenceStatus: String?
): Dto {

    override fun initializeId(newId: String): Dto {
        return this.copy(id = newId, persistenceStatus = PersistenceStatus.PERSISTED.name)
    }

}