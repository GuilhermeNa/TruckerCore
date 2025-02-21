package com.example.truckercore.modules.user.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.errors.validation.InvalidObjectException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.utils.sealeds.ValidatorInput

internal class UserValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is UserDto) processDtoValidationRules(input.dto)
        else throw IllegalValidationArgumentException(
            expected = UserDto::class, received = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is User) processEntityValidationRules(input.entity)
        else throw IllegalValidationArgumentException(
            expected = User::class, received = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is User) {
            processEntityCreationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = User::class, received = input.entity::class
        )
    }

    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        dto as UserDto
        val invalidFields = mutableListOf<String>()

        if (dto.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())

        if (dto.lastModifierId.isNullOrBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (dto.creationDate == null) invalidFields.add(Field.CREATION_DATE.getName())

        if (dto.lastUpdate == null) invalidFields.add(Field.LAST_UPDATE.getName())

        if (dto.persistenceStatus.isNullOrBlank() ||
            dto.persistenceStatus == PersistenceStatus.PENDING.name ||
            !PersistenceStatus.enumExists(dto.persistenceStatus!!)
        ) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (dto.isVip == null) invalidFields.add(Field.IS_VIP.getName())

        if (dto.vipStart == null) invalidFields.add(Field.VIP_START.getName())

        if (dto.vipEnd == null) invalidFields.add(Field.VIP_END.getName())

        if (dto.level.isNullOrBlank() ||
            !Level.enumExists(dto.level)
        ) invalidFields.add(Field.LEVEL.getName())

        if (dto.permissions.isNullOrEmpty() ||
            dto.permissions.any { !Permission.enumExists(it) }
        ) invalidFields.add(Field.PERMISSIONS.getName())

        if (dto.personFLag.isNullOrBlank() ||
            !PersonCategory.enumExists(dto.personFLag)
        ) invalidFields.add(Field.PERSON_FLAG.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(dto, invalidFields)
    }

    override fun processEntityValidationRules(entity: Entity) {
        entity as User
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isNotEmpty()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus == PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.permissions.isEmpty()) invalidFields.add(Field.PERMISSIONS.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
    }

    override fun processEntityCreationRules(entity: Entity) {
        entity as User
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isNotEmpty()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id != null) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus != PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.permissions.isEmpty()) invalidFields.add(Field.PERMISSIONS.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
    }

}