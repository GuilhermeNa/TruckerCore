package com.example.truckercore.shared.modules.storage_file.configs

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.modules.storage_file.dtos.StorageFileDto

internal object StorageFileConfigs {

    fun validateRequiredFields(dto: StorageFileDto) {
        val missingFields = mutableListOf<String>()

        if(dto.centralId.isNullOrEmpty()) missingFields.add(Field.CENTRAL_ID.getName())

        if(dto.id.isNullOrEmpty()) missingFields.add(Field.ID.getName())

        if(dto.lastModifierId.isNullOrEmpty()) missingFields.add(Field.LAST_MODIFIER_ID.getName())

        if(dto.creationDate == null) missingFields.add(Field.CREATION_DATE.getName())

        if(dto.lastUpdate == null) missingFields.add(Field.LAST_UPDATE.getName())

        if(dto.persistenceStatus.isNullOrEmpty()) missingFields.add(Field.PERSISTENCE_STATUS.getName())

        if(dto.parentId.isNullOrEmpty()) missingFields.add(Field.PARENT_ID.getName())

        if(dto.url.isNullOrEmpty()) missingFields.add(Field.URL.getName())

        if(dto.isUpdating == null) missingFields.add(Field.IS_UPDATING.getName())

        if (missingFields.isNotEmpty()) {
            throw IllegalArgumentException(missingFields.joinToString(", "))
        }

    }

}