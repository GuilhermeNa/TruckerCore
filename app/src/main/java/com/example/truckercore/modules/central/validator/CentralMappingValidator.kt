package com.example.truckercore.modules.central.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.central.dto.CentralDto

internal object CentralMappingValidator {

    fun execute(dto: CentralDto) {
        val missingFields = mutableListOf<String>()

        if(dto.centralId.isNullOrEmpty()) missingFields.add(Field.CENTRAL_ID.getName())

        if(dto.id.isNullOrEmpty()) missingFields.add(Field.ID.getName())

        if(dto.lastModifierId.isNullOrEmpty()) missingFields.add(Field.LAST_MODIFIER_ID.getName())

        if(dto.creationDate == null) missingFields.add(Field.CREATION_DATE.getName())

        if(dto.lastUpdate == null) missingFields.add(Field.LAST_UPDATE.getName())

        if(dto.persistenceStatus.isNullOrEmpty()) missingFields.add(Field.PERSISTENCE_STATUS.getName())

        if (missingFields.isNotEmpty()) {
            throw IllegalArgumentException(missingFields.joinToString(", "))
        }

    }

}