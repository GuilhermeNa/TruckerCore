package com.example.truckercore._data_provider

import com.example.truckercore.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.modules.storage_file.entities.StorageFile
import com.example.truckercore.shared.enums.PersistenceStatus
import java.time.LocalDateTime
import java.util.Date

internal object TestStorageFileDataProvider {

    fun getBaseEntity() = StorageFile(
        masterUid = "masterUid",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        parentId = "parentId",
        url = "https://www.example.com",
        isUpdating = false
    )

    fun getBaseDto() = StorageFileDto(
        masterUid = "masterUid",
        id = "id",
        lastModifierId = "id",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = "PERSISTED",
        parentId = "parentId",
        url = "https://www.example.com",
        isUpdating = false
    )

    fun getArrWithMissingFields() = arrayOf(
        getBaseDto().copy(masterUid = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(parentId = null),
        getBaseDto().copy(url = null),
        getBaseDto().copy(isUpdating = null)
    )

}

