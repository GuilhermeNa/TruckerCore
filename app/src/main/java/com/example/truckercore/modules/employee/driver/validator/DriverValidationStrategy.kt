package com.example.truckercore.modules.employee.driver.validator

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.errors.validation.InvalidObjectException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.utils.sealeds.ValidatorInput

internal class DriverValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is DriverDto) {
            processDtoValidationRules(input.dto)
        } else throw IllegalValidationArgumentException(
            expected = DriverDto::class, received = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is Driver) {
            processEntityValidationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = Driver::class, received = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is Driver) {
            processEntityCreationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = Driver::class, received = input.entity::class
        )
    }

    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        dto as DriverDto
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

        if (dto.name.isNullOrBlank()) invalidFields.add(Field.NAME.getName())

        if (dto.email.isNullOrBlank()) invalidFields.add(Field.EMAIL.getName())

        if (dto.employeeStatus.isNullOrEmpty() ||
            !EmployeeStatus.enumExists(dto.employeeStatus)
        ) invalidFields.add(Field.EMPLOYEE_STATUS.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(dto, invalidFields)
    }

    override fun processEntityValidationRules(entity: Entity) {
        entity as Driver
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isBlank()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())
        if (entity.id.isNullOrBlank()) invalidFields.add(Field.ID.getName())
        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())
        if (entity.persistenceStatus == PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())
        if (entity.name.isBlank()) invalidFields.add(Field.NAME.getName())
        if (entity.email.isBlank()) invalidFields.add(Field.EMAIL.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)
    }

    override fun processEntityCreationRules(entity: Entity) {
        entity as Driver
        val invalidFields = mutableListOf<String>()

        if (entity.businessCentralId.isEmpty()) invalidFields.add(Field.BUSINESS_CENTRAL_ID.getName())

        if (entity.id != null) invalidFields.add(Field.ID.getName())

        if (entity.lastModifierId.isBlank()) invalidFields.add(Field.LAST_MODIFIER_ID.getName())

        if (entity.persistenceStatus != PersistenceStatus.PENDING) invalidFields.add(Field.PERSISTENCE_STATUS.getName())

        if (entity.name.isEmpty()) invalidFields.add(Field.NAME.getName())

        if (entity.email.isEmpty()) invalidFields.add(Field.EMAIL.getName())

        if (invalidFields.isNotEmpty()) throw InvalidObjectException(entity, invalidFields)

    }

}
