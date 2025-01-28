package com.example.truckercore.shared.modules.personal_data.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.errors.PersonalDataValidationException
import com.example.truckercore.shared.sealeds.ValidatorInput
import com.example.truckercore.shared.utils.expressions.logError
import kotlin.reflect.KClass

internal class PersonalDataValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is PersonalDataDto) {
            processDtoValidationRules(input.dto)
        } else handleUnexpectedInputError(
            expectedClass = PersonalDataDto::class, inputClass = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is PersonalData) {
            processEntityValidationRules(input.entity)
        } else handleUnexpectedInputError(
            expectedClass = PersonalData::class, inputClass = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is PersonalData) {
            processEntityCreationRules(input.entity)
        } else handleUnexpectedInputError(
            expectedClass = PersonalData::class, inputClass = input.entity::class
        )
    }

    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        dto as PersonalDataDto
        val invalidFields = mutableListOf<String>()

        if (dto.businessCentralId.isNullOrBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (dto.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())

        if (dto.lastModifierId.isNullOrBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (dto.creationDate == null) invalidFields.add(Field.CREATION_DATE.getName())

        if (dto.lastUpdate == null) invalidFields.add(Field.LAST_UPDATE.getName())

        if (dto.persistenceStatus.isNullOrBlank() ||
            dto.persistenceStatus == PersistenceStatus.PENDING.name ||
            !PersistenceStatus.enumExists(dto.persistenceStatus!!)
        ) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (dto.parentId.isNullOrBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (dto.name.isNullOrBlank()) invalidFields.add(Field.NAME.getName())

        if (dto.number.isNullOrBlank()) invalidFields.add(Field.NUMBER.getName())

        if (dto.emissionDate == null) invalidFields.add(Field.EMISSION_DATE.getName())

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(dto::class, invalidFields)
    }

    override fun processEntityValidationRules(entity: Entity) {
        entity as PersonalData
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus == PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.parentId.isBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (entity.name.isBlank()) invalidFields.add(Field.NAME.getName())

        if (entity.number.isBlank()) invalidFields.add(Field.NUMBER.getName())

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(entity::class, invalidFields)
    }

    override fun processEntityCreationRules(entity: Entity) {
        entity as PersonalData
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isNotEmpty()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id != null) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus != PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.parentId.isBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (entity.name.isBlank()) invalidFields.add(Field.NAME.getName())

        if (entity.number.isBlank()) invalidFields.add(Field.NUMBER.getName())

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(entity::class, invalidFields)
    }

    override fun <T : KClass<*>> handleInvalidFieldsErrors(obj: T, fields: List<String>) {
        val message = "Invalid ${obj.simpleName}." +
                " Missing or invalid fields: ${fields.joinToString(", ")}."
        logError("${this.javaClass.simpleName}: $message")
        throw PersonalDataValidationException(message)
    }

}