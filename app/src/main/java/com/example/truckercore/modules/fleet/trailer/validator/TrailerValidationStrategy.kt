package com.example.truckercore.modules.fleet.trailer.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.trailer.enums.TrailerBrand
import com.example.truckercore.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.errors.validation.InvalidObjectException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.utils.sealeds.ValidatorInput

internal class TrailerValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is TrailerDto) {
            processDtoValidationRules(input.dto)
        } else throw IllegalValidationArgumentException(
            expected = TrailerDto::class, received = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is Trailer) {
            processEntityValidationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = Trailer::class, received = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is Trailer) {
            processEntityCreationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = Trailer::class, received = input.entity::class
        )
    }

    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        dto as TrailerDto
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

        if (dto.plate.isNullOrBlank()) invalidFields.add(Field.PLATE.getName())

        if (dto.color.isNullOrBlank()) invalidFields.add(Field.COLOR.getName())

        if (dto.brand.isNullOrEmpty() ||
            !TrailerBrand.enumExists(dto.brand)
        ) invalidFields.add(Field.BRAND.getName())

        if (dto.category.isNullOrEmpty() ||
            !TrailerCategory.enumExists(dto.category)
        ) invalidFields.add(Field.CATEGORY.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(dto, invalidFields)
    }

    override fun processEntityValidationRules(entity: Entity) {
        entity as Trailer
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())
        if (entity.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())
        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())
        if (entity.persistenceStatus == PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        if (entity.plate.isBlank()) invalidFields.add(Field.PLATE.getName())
        if (entity.color.isBlank()) invalidFields.add(Field.COLOR.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
    }

    override fun processEntityCreationRules(entity: Entity) {
        entity as Trailer
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isEmpty()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id != null) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus != PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.plate.isEmpty()) invalidFields.add(Field.PLATE.getName())

        if (entity.color.isEmpty()) invalidFields.add(Field.COLOR.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
    }

}