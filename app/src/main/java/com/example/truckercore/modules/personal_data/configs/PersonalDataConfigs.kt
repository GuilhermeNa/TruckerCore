package com.example.truckercore.modules.personal_data.configs

import com.example.truckercore.configs.database.Field
import com.example.truckercore.modules.personal_data.dtos.PersonalDataDto

internal object PersonalDataConfigs {

    fun validateRequiredFields(dto: PersonalDataDto) {
        val missingFields = mutableListOf<String>()

        if(dto.masterUid.isNullOrEmpty()) missingFields.add(Field.MASTER_UID)

        if(dto.id.isNullOrEmpty()) missingFields.add(Field.ID)

        if(dto.lastModifierId.isNullOrEmpty()) missingFields.add(Field.LAST_MODIFIER_ID)

        if(dto.creationDate == null) missingFields.add(Field.CREATION_DATE)

        if(dto.lastUpdate == null) missingFields.add(Field.LAST_UPDATE)

        if(dto.persistenceStatus.isNullOrEmpty()) missingFields.add(Field.PERSISTENCE_STATUS)

        if(dto.parentId.isNullOrEmpty()) missingFields.add(Field.PARENT_ID)

        if(dto.name.isNullOrEmpty()) missingFields.add(Field.NAME)

        if(dto.number.isNullOrEmpty()) missingFields.add(Field.NUMBER)

        if(dto.emissionDate == null) missingFields.add(Field.EMISSION_DATE)

        if (missingFields.isNotEmpty()) {
            throw IllegalArgumentException(missingFields.joinToString(", "))
        }

    }


}