package com.example.truckercore._test_data_provider

import com.example.truckercore.modules.central.dto.CentralDto
import com.example.truckercore.modules.central.entity.Central
import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime
import java.util.Date

internal object TestCentralDataProvider {

    fun getBaseEntity() = Central(
        centralId = "centralId",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED
    )

    fun getBaseDto() = CentralDto(
        centralId = "centralId",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name
    )

    fun getArrWithMissingFields() = arrayOf(
        getBaseDto().copy(centralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null)
    )

    fun getArrForTestingCreationValidator() = arrayOf(
        getBaseDto().copy(id = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(centralId = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, centralId = null, lastModifierId = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, centralId = null, lastModifierId = "", persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, centralId = null, creationDate = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, centralId = null, lastUpdate = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, centralId = null, persistenceStatus = null),
        getBaseDto().copy(id = null, centralId = null, persistenceStatus = PersistenceStatus.PERSISTED.name)
    )

}