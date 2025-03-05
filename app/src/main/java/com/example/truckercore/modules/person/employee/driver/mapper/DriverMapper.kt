package com.example.truckercore.modules.person.employee.driver.mapper

import com.example.truckercore.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.modules.person.employee.driver.entity.Driver
import com.example.truckercore.modules.person.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class DriverMapper : Mapper {

    override fun toEntity(dto: Dto): Driver =
        if (dto is DriverDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = DriverDto::class,
            received = dto::class
        )

    override fun toDto(entity: Entity): DriverDto =
        if (entity is Driver) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = Driver::class,
            received = entity::class
        )

    private fun convertToEntity(dto: DriverDto) = try {
        Driver(
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

    private fun convertToDto(entity: Driver) = try {
        DriverDto(
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
