package com.example.truckercore.modules.business_central.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.shared.enums.PersistenceStatus

internal object CentralCreationValidator {

    fun execute(dto: BusinessCentralDto) {
        val illegalFields = mutableListOf<String>()

        if (!dto.centralId.isNullOrEmpty()) {
            illegalFields.add("[${Field.CENTRAL_ID.name} -> expecting: nullOrEmpty, actual: ${dto.centralId}]")
        }
        if (!dto.id.isNullOrEmpty()) {
            illegalFields.add("[${Field.ID.name} -> expecting: nullOrEmpty, actual: ${dto.id}]")
        }
        if (dto.persistenceStatus != PersistenceStatus.PENDING.name) {
            illegalFields.add("[${Field.PERSISTENCE_STATUS.name} -> expecting: ${PersistenceStatus.PENDING}, actual: ${dto.persistenceStatus}]")
        }
        if (dto.lastModifierId.isNullOrEmpty()) {
            illegalFields.add("[${Field.LAST_MODIFIER_ID} -> cannot be null or empty]")
        }
        if (dto.creationDate == null) {
            illegalFields.add("[${Field.CREATION_DATE} -> cannot be null]")
        }
        if (dto.lastUpdate == null) {
            illegalFields.add(" [${Field.LAST_UPDATE} -> cannot be null]")
        }

        if(illegalFields.isNotEmpty()) throw IllegalArgumentException(
            " Error while validating a Central dto for creation process: ${illegalFields.joinToString(", ")}"
        )

    }

}