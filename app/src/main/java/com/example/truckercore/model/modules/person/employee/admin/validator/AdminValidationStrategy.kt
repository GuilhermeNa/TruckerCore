package com.example.truckercore.model.modules.person.employee.admin.validator

import com.example.truckercore.model.configs.app_constants.Field
import com.example.truckercore.model.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.person.employee.shared.enums.EmployeeStatus
import com.example.truckercore.model.shared.abstractions.ValidatorStrategy
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.model.shared.errors.validation.InvalidObjectException
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.interfaces.Entity
import com.example.truckercore.model.shared.utils.sealeds.ValidatorInput

internal class AdminValidationStrategy : ValidatorStrategy() {

    override fun validateDto(input: ValidatorInput.DtoInput) {
        if (input.dto is AdminDto) {
            processDtoValidationRules(input.dto)
        } else throw IllegalValidationArgumentException(
            expected = AdminDto::class, received = input.dto::class
        )
    }

    override fun validateEntity(input: ValidatorInput.EntityInput) {
        if (input.entity is Admin) {
            processEntityValidationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = Admin::class, received = input.entity::class
        )
    }

    override fun validateForCreation(input: ValidatorInput.EntityInput) {
        if (input.entity is Admin) {
            processEntityCreationRules(input.entity)
        } else throw IllegalValidationArgumentException(
            expected = Admin::class, received = input.entity::class
        )
    }

    //----------------------------------------------------------------------------------------------

    override fun processDtoValidationRules(dto: Dto) {
        dto as AdminDto
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
        entity as Admin
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
        entity as Admin
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
