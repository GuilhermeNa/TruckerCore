package com.example.truckercore.shared.modules.storage_file.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.errors.StorageFileValidationException
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.sealeds.ValidatorInput
import kotlin.reflect.KClass

internal class StorageFileValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is StorageFileDto) {
            processDtoValidationRules(input.dto)
        } else handleUnexpectedInputError(
            expectedClass = StorageFileDto::class, inputClass = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is StorageFile) {
            processEntityValidationRules(input.entity)
        } else handleUnexpectedInputError(
            expectedClass = StorageFile::class, inputClass = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is StorageFile) {
            processEntityCreationRules(input.entity)
        } else handleUnexpectedInputError(
            expectedClass = StorageFile::class, inputClass = input.entity::class
        )
    }

    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        dto as StorageFileDto
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

        if (dto.url.isNullOrBlank()) invalidFields.add(Field.URL.getName())

        if (dto.isUpdating == null) invalidFields.add(Field.IS_UPDATING.getName())

        if (invalidFields.isNotEmpty()) handleValidationErrors(dto::class, invalidFields)
    }

    override fun processEntityValidationRules(entity: Entity) {
        entity as StorageFile
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus == PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.parentId.isBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (entity.url.isBlank()) invalidFields.add(Field.URL.getName())

        if (invalidFields.isNotEmpty()) handleValidationErrors(entity::class, invalidFields)
    }

    override fun processEntityCreationRules(entity: Entity) {
        entity as StorageFile
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id != null) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus != PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.parentId.isBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (entity.url.isBlank()) invalidFields.add(Field.URL.getName())

        if (invalidFields.isNotEmpty()) handleValidationErrors(entity::class, invalidFields)
    }

    override fun <T : KClass<*>> handleValidationErrors(obj: T, fields: List<String>) {
        val message = "Invalid ${obj.simpleName}." +
                " Missing or invalid fields: ${fields.joinToString(", ")}."
        logWarn(
            context = javaClass,
            message = message
        )
        throw StorageFileValidationException(message)
    }

}