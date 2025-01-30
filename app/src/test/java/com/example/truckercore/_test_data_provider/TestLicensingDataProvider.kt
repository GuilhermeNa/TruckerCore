package com.example.truckercore._test_data_provider

import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime
import java.util.Date

internal object TestLicensingDataProvider {

    fun getBaseEntity() = Licensing(
        businessCentralId = "123",
        id = "id",
        lastModifierId = "modifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        parentId = "parentId",
        emissionDate = LocalDateTime.now(),
        expirationDate = LocalDateTime.now(),
        plate = "PLATE123",
        exercise = LocalDateTime.now()
    )

    fun getBaseDto() = LicensingDto(
        businessCentralId = "123",
        id = "id",
        lastModifierId = "modifierId",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name,
        parentId = "parentId",
        emissionDate = Date(),
        expirationDate = Date(),
        plate = "PLATE123",
        exercise = Date()
    )

    fun arrInvalidDtosForMapping() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(parentId = null),
        getBaseDto().copy(emissionDate = null),
        getBaseDto().copy(expirationDate = null),
        getBaseDto().copy(plate = null),
        getBaseDto().copy(exercise = null)
    )

    fun arrValidDtosForValidationRules() = arrayOf(
        getBaseDto(),
        getBaseDto().copy(persistenceStatus = "ARCHIVED")
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
        getBaseDto().copy(emissionDate = null),
        getBaseDto().copy(expirationDate = null),
        getBaseDto().copy(plate = null),
        getBaseDto().copy(plate = ""),
        getBaseDto().copy(plate = " "),
        getBaseDto().copy(exercise = null)
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
        getBaseEntity().copy(plate = ""),
        getBaseEntity().copy(plate = " ")
    )

    fun arrValidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PENDING)
    )

    fun arrInvalidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = ""),
        getBaseEntity().copy(businessCentralId = " "),
        getBaseEntity().copy(),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PERSISTED),
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.ARCHIVED),
        getBaseEntity().copy(parentId = ""),
        getBaseEntity().copy(parentId = " "),
        getBaseEntity().copy(plate = ""),
        getBaseEntity().copy(plate = " ")
    )

}