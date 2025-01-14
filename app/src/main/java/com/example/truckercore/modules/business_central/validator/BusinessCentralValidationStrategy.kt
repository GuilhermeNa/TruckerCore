package com.example.truckercore.modules.business_central.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.business_central.errors.BusinessCentralValidationException
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity

internal class  BusinessCentralValidationStrategy : ValidatorStrategy() {

    override fun validateDto(dto: Dto) {
        processDtoValidationRules(dto)
    }

    override fun validateEntity(entity: Entity) {
        processEntityValidationRules(entity)
    }

    override fun validateForCreation(entity: Entity) {
        processEntityCreationRules(entity)
    }
//TODO(" garantir o tipo")
    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        val invalidFields = mutableListOf<String>()

        if (dto.businessCentralId.isNullOrBlank()) {
            invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())
        }
        if (dto.id.isNullOrBlank()) {
            invalidFields.add(Field.ID.getName())
        }
        if (dto.lastModifierId.isNullOrBlank()) {
            invalidFields.add(Field.LAST_MODIFIER_ID.getName())
        }
        if (dto.creationDate == null) {
            invalidFields.add(Field.CREATION_DATE.getName())
        }
        if (dto.lastUpdate == null) {
            invalidFields.add(Field.LAST_UPDATE.getName())
        }
        if (dto.persistenceStatus.isNullOrBlank() ||
            dto.persistenceStatus == PersistenceStatus.PENDING.name ||
            !PersistenceStatus.enumExists(dto.persistenceStatus!!)
        ) {
            invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        }

        if (invalidFields.isNotEmpty()) {
            throw BusinessCentralValidationException(
                "Error while validating a Business Central dto, invalid fields:" +
                        " ${invalidFields.joinToString(", ")}."
            )
        }

    }

    override fun processEntityValidationRules(entity: Entity) {
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) {
            invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())
        }
        if (entity.id.isNullOrBlank()) {
            invalidFields.add(Field.ID.getName())
        }
        if (entity.lastModifierId.isBlank()) {
            invalidFields.add(Field.LAST_MODIFIER_ID.getName())
        }
        if (entity.persistenceStatus == PersistenceStatus.PENDING) {
            invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        }

        if (invalidFields.isNotEmpty()) {
            throw BusinessCentralValidationException(
                "Error while validating a Business Central entity, invalid fields:" +
                        " ${invalidFields.joinToString(", ")}."
            )
        }
    }

    override fun processEntityCreationRules(entity: Entity) {
        val invalidFields = mutableListOf<String>()

        if (!entity.id.isNullOrBlank()) {
            invalidFields.add(Field.ID.getName())
        }
        if (entity.lastModifierId.isBlank()) {
            invalidFields.add(Field.LAST_MODIFIER_ID.getName())
        }
        if (entity.persistenceStatus != PersistenceStatus.PENDING) {
            invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        }

        if (invalidFields.isNotEmpty()) {
            throw BusinessCentralValidationException(
                "Error while validating a Business Central entity for creation, invalid fields:" +
                        " ${invalidFields.joinToString(", ")}."
            )
        }
    }

}