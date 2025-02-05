package com.example.truckercore.modules.fleet.shared.module.licensing.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.errors.LicensingValidationException
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.sealeds.ValidatorInput
import kotlin.reflect.KClass

internal class LicensingValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is LicensingDto) {
            processDtoValidationRules(input.dto)
        } else handleUnexpectedInputError(
            expectedClass = LicensingDto::class,
            inputClass = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is Licensing) {
            processEntityValidationRules(input.entity)
        } else handleUnexpectedInputError(
            expectedClass = Licensing::class,
            inputClass = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is Licensing) {
            processEntityCreationRules(input.entity)
        } else handleUnexpectedInputError(
            expectedClass = Licensing::class,
            inputClass = input.entity::class
        )
    }

    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        dto as LicensingDto
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

        if (dto.emissionDate == null) invalidFields.add(Field.EMISSION_DATE.getName())

        if (dto.expirationDate == null) invalidFields.add(Field.EXPIRATION_DATE.getName())

        if (dto.plate.isNullOrBlank()) invalidFields.add(Field.PLATE.getName())

        if (dto.exercise == null) invalidFields.add(Field.EXERCISE.getName())

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(dto::class, invalidFields)
    }

    override fun processEntityValidationRules(entity: Entity) {
        entity as Licensing
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id.isNullOrBlank()) {
            invalidFields.add(Field.ID.getName())
        }

        if (entity.lastModifierId.isBlank()) {
            invalidFields.add(Field.LAST_MODIFIER_ID.getName())
        }

        if (entity.persistenceStatus == PersistenceStatus.PENDING) {
            invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        }

        if (entity.parentId.isBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (entity.plate.isBlank()) invalidFields.add(Field.PLATE.getName())

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(entity::class, invalidFields)
    }

    override fun processEntityCreationRules(entity: Entity) {
        entity as Licensing
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (!entity.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus != PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.parentId.isBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (entity.plate.isBlank()) invalidFields.add(Field.PLATE.getName())

        if (invalidFields.isNotEmpty()) handleInvalidFieldsErrors(entity::class, invalidFields)
    }

    override fun <T : KClass<*>> handleInvalidFieldsErrors(obj: T, fields: List<String>) {
        val message = "Invalid ${obj.simpleName}." +
                " Missing or invalid fields: ${fields.joinToString(", ")}."
        logWarn(
            context = javaClass,
            message = message
        )
        throw LicensingValidationException(message)
    }

}