package com.example.truckercore.model.modules.notification.validator

import com.example.truckercore.model.configs.app_constants.Field
import com.example.truckercore.model.configs.app_constants.Parent
import com.example.truckercore.model.modules.notification.dto.NotificationDto
import com.example.truckercore.model.modules.notification.entity.Notification
import com.example.truckercore.model.shared.abstractions.ValidatorStrategy
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.model.shared.errors.validation.InvalidObjectException
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.interfaces.Entity
import com.example.truckercore.model.shared.utils.sealeds.ValidatorInput

internal class NotificationValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is NotificationDto) {
            processDtoValidationRules(input.dto)
        } else throw IllegalValidationArgumentException(
            expected = NotificationDto::class, received = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is Notification) {
            processEntityValidationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = Notification::class, received = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is Notification) {
            processEntityCreationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = Notification::class, received = input.entity::class
        )
    }

    override fun processDtoValidationRules(dto: Dto) {
        dto as NotificationDto
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

        if (dto.title.isNullOrBlank()) invalidFields.add(Field.TITLE.getName())

        if (dto.message.isNullOrBlank()) invalidFields.add(Field.MESSAGE.getName())

        if (dto.isRead == null) invalidFields.add(Field.IS_READ.getName())

        if (dto.parent.isNullOrBlank() ||
            !Parent.enumExists(dto.parent)
        ) invalidFields.add(Field.PARENT.getName())

        if (dto.parentId.isNullOrBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(dto, invalidFields)
    }

    override fun processEntityValidationRules(entity: Entity) {
        entity as Notification
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus == PersistenceStatus.PENDING) {
            invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        }
        if (entity.title.isBlank()) invalidFields.add(Field.TITLE.getName())

        if (entity.message.isBlank()) invalidFields.add(Field.MESSAGE.getName())

        if (entity.parentId.isBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
    }

    override fun processEntityCreationRules(entity: Entity) {
        entity as Notification
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (!entity.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus != PersistenceStatus.PENDING) {
            invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        }

        if (entity.title.isBlank()) invalidFields.add(Field.TITLE.getName())

        if (entity.message.isBlank()) invalidFields.add(Field.MESSAGE.getName())

        if (entity.parentId.isBlank()) invalidFields.add(Field.PARENT_ID.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
    }

}