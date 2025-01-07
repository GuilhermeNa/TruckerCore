package com.example.truckercore._test_data_provider

import com.example.truckercore.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.modules.personal_data.entities.PersonalData
import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime
import java.util.Date

internal object TestPersonalDataDataProvider {

    fun getBaseEntity() = PersonalData(
        masterUid = "masterUid",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        parentId = "parentId",
        name = "name",
        number = "number",
        emissionDate = LocalDateTime.now(),
        expirationDate = LocalDateTime.now()
    )

    fun getBaseDto() = PersonalDataDto(
        masterUid = "masterUid",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name,
        parentId = "parentId",
        name = "name",
        number = "number",
        emissionDate = Date(),
        expirationDate = Date()
    )

    fun getArrWithMissingFields() = arrayOf(
        getBaseDto().copy(masterUid = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(parentId = null),
        getBaseDto().copy(name = null),
        getBaseDto().copy(number = null),
        getBaseDto().copy(emissionDate = null)
    )

}