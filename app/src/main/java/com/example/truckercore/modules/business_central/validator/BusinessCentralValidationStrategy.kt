package com.example.truckercore.modules.business_central.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.errors.validation.InvalidObjectException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.utils.sealeds.ValidatorInput

internal class BusinessCentralValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is BusinessCentralDto) {
            processDtoValidationRules(input.dto)
        } else throw IllegalValidationArgumentException(
            expected = BusinessCentralDto::class, received = input.dto
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is BusinessCentral) {
            processEntityValidationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = BusinessCentral::class, received = input.entity
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is BusinessCentral) {
            processEntityCreationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = BusinessCentral::class, received = input.entity
        )
    }

    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        val invalidFields = mutableListOf<String>()

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

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(dto, invalidFields)
    }

    override fun processEntityValidationRules(entity: Entity) {
        val invalidFields = mutableListOf<String>()

        if (entity.id.isNullOrBlank()) {
            invalidFields.add(Field.ID.getName())
        }
        if (entity.lastModifierId.isBlank()) {
            invalidFields.add(Field.LAST_MODIFIER_ID.getName())
        }
        if (entity.persistenceStatus == PersistenceStatus.PENDING) {
            invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        }

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
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

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
    }

}