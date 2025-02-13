package com.example.truckercore.modules.business_central.mapper

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class BusinessCentralMapper : Mapper {

    override fun toEntity(dto: Dto): BusinessCentral =
        if (dto is BusinessCentralDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = BusinessCentralDto::class,
            received = dto::class
        )

    override fun toDto(entity: Entity): BusinessCentralDto =
        if (entity is BusinessCentral) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = BusinessCentral::class,
            received = entity::class
        )

    private fun convertToEntity(dto: BusinessCentralDto) = try {
        BusinessCentral(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!)
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: BusinessCentral) = try {
        BusinessCentralDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}
