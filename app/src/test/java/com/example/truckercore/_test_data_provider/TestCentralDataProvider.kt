package com.example.truckercore._test_data_provider

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime
import java.util.Date

internal object TestCentralDataProvider {

    fun getBaseEntity() = BusinessCentral(
        businessCentralId = "centralId",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED
    )

    fun getBaseDto() = BusinessCentralDto(
        businessCentralId = "centralId",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name
    )

    fun getArrWithMissingFields() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null)
    )

    fun getArrForTestingCreationValidator() = arrayOf(
        getBaseDto().copy(id = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(businessCentralId = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, businessCentralId = null, lastModifierId = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, businessCentralId = null, lastModifierId = "", persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, businessCentralId = null, creationDate = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, businessCentralId = null, lastUpdate = null, persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(id = null, businessCentralId = null, persistenceStatus = null),
        getBaseDto().copy(id = null, businessCentralId = null, persistenceStatus = PersistenceStatus.PERSISTED.name)
    )

}