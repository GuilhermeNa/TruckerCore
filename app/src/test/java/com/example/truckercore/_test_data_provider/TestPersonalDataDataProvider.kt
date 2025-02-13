package com.example.truckercore._test_data_provider

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import java.time.LocalDateTime
import java.util.Date

internal object TestPersonalDataDataProvider {

    fun getBaseEntity() = PersonalData(
        businessCentralId = "masterUid",
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
        businessCentralId = "masterUid",
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

    fun getDtoToSave() = getBaseDto().copy(
        id = null, persistenceStatus = PersistenceStatus.PENDING.name
    )

    fun arrInvalidDtos() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
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

    fun arrValidDtosForValidationRules() = arrayOf(
        getBaseDto(),
        getBaseDto().copy(persistenceStatus = PersistenceStatus.ARCHIVED.name)
    )

    fun arrInvalidDtosForValidationRules() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(businessCentralId = ""),
        getBaseDto().copy(businessCentralId = " "),
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
        getBaseDto().copy(persistenceStatus = "PENDING"),
        getBaseDto().copy(persistenceStatus = "INVALID"),
        getBaseDto().copy(parentId = null),
        getBaseDto().copy(parentId = ""),
        getBaseDto().copy(parentId = " "),
        getBaseDto().copy(name = null),
        getBaseDto().copy(name = ""),
        getBaseDto().copy(name = " "),
        getBaseDto().copy(number = null),
        getBaseDto().copy(number = ""),
        getBaseDto().copy(number = " "),
        getBaseDto().copy(emissionDate = null)
    )

    fun arrValidEntitiesForValidationRules() = arrayOf(
        getBaseEntity(),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED)
    )

    fun arrInvalidEntitiesForValidationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = ""),
        getBaseEntity().copy(businessCentralId = " "),
        getBaseEntity().copy(id = null),
        getBaseEntity().copy(id = ""),
        getBaseEntity().copy(id = " "),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PENDING),
        getBaseEntity().copy(parentId = ""),
        getBaseEntity().copy(parentId = " "),
        getBaseEntity().copy(name = ""),
        getBaseEntity().copy(name = " "),
        getBaseEntity().copy(number = ""),
        getBaseEntity().copy(number = " ")
    )

    fun arrValidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PENDING)
    )

    fun arrInvalidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = ""),
        getBaseEntity().copy(businessCentralId = " "),
        getBaseEntity().copy(id = "123"),
        getBaseEntity().copy(),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PERSISTED),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED),
        getBaseEntity().copy(parentId = ""),
        getBaseEntity().copy(parentId = " "),
        getBaseEntity().copy(name = ""),
        getBaseEntity().copy(name = " "),
        getBaseEntity().copy(number = ""),
        getBaseEntity().copy(number = " ")
    )

}