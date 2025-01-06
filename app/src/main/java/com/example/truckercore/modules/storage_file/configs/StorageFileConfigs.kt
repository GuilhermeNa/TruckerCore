package com.example.truckercore.modules.storage_file.configs

import com.example.truckercore.configs.database.Field
import com.example.truckercore.modules.storage_file.dtos.StorageFileDto

internal object StorageFileConfigs {

    fun validateRequiredFields(dto: StorageFileDto) {
        val missingFields = mutableListOf<String>()

        if(dto.masterUid.isNullOrEmpty()) missingFields.add(Field.MASTER_UID)

        if(dto.id.isNullOrEmpty()) missingFields.add(Field.ID)

        if(dto.lastModifierId.isNullOrEmpty()) missingFields.add(Field.LAST_MODIFIER_ID)

        if(dto.creationDate == null) missingFields.add(Field.CREATION_DATE)

        if(dto.lastUpdate == null) missingFields.add(Field.LAST_UPDATE)

        if(dto.persistenceStatus.isNullOrEmpty()) missingFields.add(Field.PERSISTENCE_STATUS)

        if(dto.parentId.isNullOrEmpty()) missingFields.add(Field.PARENT_ID)

        if(dto.url.isNullOrEmpty()) missingFields.add(Field.URL)

        if(dto.isUpdating == null) missingFields.add(Field.IS_UPDATING)

        if (missingFields.isNotEmpty()) {
            throw IllegalArgumentException(missingFields.joinToString(", "))
        }

    }

}