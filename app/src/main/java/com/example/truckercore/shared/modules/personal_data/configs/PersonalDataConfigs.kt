package com.example.truckercore.shared.modules.personal_data.configs

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto

internal object PersonalDataConfigs {

    fun validateRequiredFields(dto: PersonalDataDto) {
        val missingFields = mutableListOf<String>()

        if(dto.masterUid.isNullOrEmpty()) missingFields.add(Field.MASTER_UID.getName())

        if(dto.id.isNullOrEmpty()) missingFields.add(Field.ID.getName())

        if(dto.lastModifierId.isNullOrEmpty()) missingFields.add(Field.LAST_MODIFIER_ID.getName())

        if(dto.creationDate == null) missingFields.add(Field.CREATION_DATE.getName())

        if(dto.lastUpdate == null) missingFields.add(Field.LAST_UPDATE.getName())

        if(dto.persistenceStatus.isNullOrEmpty()) missingFields.add(Field.PERSISTENCE_STATUS.getName())

        if(dto.parentId.isNullOrEmpty()) missingFields.add(Field.PARENT_ID.getName())

        if(dto.name.isNullOrEmpty()) missingFields.add(Field.NAME.getName())

        if(dto.number.isNullOrEmpty()) missingFields.add(Field.NUMBER.getName())

        if(dto.emissionDate == null) missingFields.add(Field.EMISSION_DATE.getName())

        if (missingFields.isNotEmpty()) {
            throw IllegalArgumentException(missingFields.joinToString(", "))
        }

    }


}