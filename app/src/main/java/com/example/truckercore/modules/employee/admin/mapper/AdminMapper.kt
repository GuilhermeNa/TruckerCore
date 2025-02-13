package com.example.truckercore.modules.employee.admin.mapper

import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class AdminMapper : Mapper {

    override fun toEntity(dto: Dto): Admin =
        if (dto is AdminDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = AdminDto::class,
            received = dto::class
        )

    override fun toDto(entity: Entity): AdminDto =
        if (entity is Admin) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = Admin::class,
            received = entity::class
        )

    private fun convertToEntity(dto: AdminDto) = try {
        Admin(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
            userId = dto.userId,
            name = dto.name!!,
            email = dto.email!!,
            employeeStatus = EmployeeStatus.convertString(dto.employeeStatus)
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: Admin) = try {
        AdminDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            userId = entity.userId,
            name = entity.name,
            email = entity.email,
            employeeStatus = entity.employeeStatus.name
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}
