package com.example.truckercore._test_data_provider

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime
import java.util.Date

internal object TestBusinessCentralDataProvider {

    fun getBaseEntity() = BusinessCentral(
        businessCentralId = "centralId",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        keys = 1
    )

    fun getBaseDto() = BusinessCentralDto(
        businessCentralId = "centralId",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name,
        keys = 1
    )

    fun arrValidDtosForValidationRules() = arrayOf(
        getBaseDto(),
        getBaseDto().copy(persistenceStatus = PersistenceStatus.ARCHIVED.name)
    )

    fun arrInvalidDtosForValidationRules() = arrayOf(
        getBaseDto().copy(id = null),
        getBaseDto().copy(id = ""),
        getBaseDto().copy(id = " "),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(lastModifierId = ""),
        getBaseDto().copy(lastModifierId = " "),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(persistenceStatus = ""),
        getBaseDto().copy(persistenceStatus = " "),
        getBaseDto().copy(persistenceStatus = PersistenceStatus.PENDING.name),
        getBaseDto().copy(keys = null),
    )

    fun arrInvalidEntitiesForValidationRules() = arrayOf(
        getBaseEntity().copy(id = null),
        getBaseEntity().copy(id = ""),
        getBaseEntity().copy(id = " "),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PENDING)
    )

    fun arrInvalidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = "hasId"),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PERSISTED),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED)
    )

    fun arrValidEntitiesForValidationRules() = arrayOf(
        getBaseEntity(),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED)
    )

    fun arrValidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PENDING),
        getBaseEntity().copy(id = "", persistenceStatus = PersistenceStatus.PENDING)
    )

    fun arrInvalidDtos() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(persistenceStatus = "INVALID"),
        getBaseDto().copy(keys = null)
    )

}