package com.example.truckercore.modules.business_central.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.errors.BusinessCentralValidationException
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.sealeds.ValidatorInput
import com.example.truckercore.shared.utils.expressions.logError
import kotlin.reflect.KClass

internal class BusinessCentralValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is BusinessCentralDto) {
            processDtoValidationRules(input.dto)
        } else handleUnexpectedInputError(
            expectedClass = BusinessCentralDto::class,
            inputClass = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is BusinessCentral) {
            processEntityValidationRules(input.entity)
        } else handleUnexpectedInputError(
            expectedClass = BusinessCentral::class,
            inputClass = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is BusinessCentral) {
            processEntityCreationRules(input.entity)
        } else handleUnexpectedInputError(
            expectedClass = BusinessCentral::class,
            inputClass = input.entity::class
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

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(dto::class, invalidFields)
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

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(entity::class, invalidFields)
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

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(entity::class, invalidFields)
    }

    override fun <T : KClass<*>> handleInvalidFieldsErrors(obj: T, fields: List<String>) {
        val message = "Invalid ${obj.simpleName}." +
                " Missing or invalid fields: ${fields.joinToString(", ")}."
        logError("${this.javaClass.simpleName}: $message")
        throw BusinessCentralValidationException(message)
    }

}