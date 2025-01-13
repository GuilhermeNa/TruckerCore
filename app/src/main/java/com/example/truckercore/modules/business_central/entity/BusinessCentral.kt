package com.example.truckercore.modules.business_central.entity

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

data class BusinessCentral(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus
): Entity {



}