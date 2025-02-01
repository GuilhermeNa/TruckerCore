package com.example.truckercore.modules.fleet.shared.module.licensing.entity

import com.example.truckercore.modules.fleet.shared.abstraction.FleetData
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import java.time.LocalDateTime

data class Licensing(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    override val parentId: String,
    override val emissionDate: LocalDateTime,
    val expirationDate: LocalDateTime,
    val plate: String,
    val exercise: LocalDateTime
) : FleetData(parentId, emissionDate), Entity