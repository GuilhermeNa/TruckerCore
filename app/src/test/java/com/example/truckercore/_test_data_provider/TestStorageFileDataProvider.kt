package com.example.truckercore._test_data_provider

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.modules.file.dto.FileDto
import com.example.truckercore.shared.modules.file.entity.File
import java.time.LocalDateTime
import java.util.Date

internal object TestStorageFileDataProvider {

    fun getBaseEntity() = File(
        businessCentralId = "masterUid",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        parentId = "parentId",
        url = "https://www.example.com",
        isUpdating = false
    )

    fun getBaseDto() = FileDto(
        businessCentralId = "masterUid",
        id = "id",
        lastModifierId = "id",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = "PERSISTED",
        parentId = "parentId",
        url = "https://www.example.com",
        isUpdating = false
    )

    fun arrInvalidDtos() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(parentId = null),
        getBaseDto().copy(url = null),
        getBaseDto().copy(isUpdating = null)
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
        getBaseDto().copy(url = null),
        getBaseDto().copy(url = ""),
        getBaseDto().copy(url = " "),
        getBaseDto().copy(isUpdating = null)
    )

    fun arrValidDtosForValidationRules() = arrayOf(
        getBaseDto(),
        getBaseDto().copy(persistenceStatus = "ARCHIVED") // Exemplo de um dto válido
    )

    fun arrValidEntitiesForValidationRules() = arrayOf(
        getBaseEntity(),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED) // Exemplo de entidade válida
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
        getBaseEntity().copy(parentId = " ")
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
        getBaseEntity().copy(parentId = " ")
    )

}

