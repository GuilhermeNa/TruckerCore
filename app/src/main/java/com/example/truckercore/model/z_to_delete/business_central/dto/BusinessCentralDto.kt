/*
package com.example.truckercore.model.modules._previous_sample.business_central.dto

import java.util.Date

internal data class BusinessCentralDto(
    override val businessCentralId: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    val authorizedUserIds: HashSet<String>? = null,
    val keys: Int? = null
) : Dto {

    override fun initializeId(newId: String): BusinessCentralDto {
        return this.copy(id = newId, persistenceStatus = PersistenceStatus.PERSISTED.name)
    }

}*/
