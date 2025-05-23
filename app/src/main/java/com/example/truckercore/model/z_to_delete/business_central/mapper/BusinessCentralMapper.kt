/*
package com.example.truckercore.model.modules._previous_sample.business_central.mapper

import com.example.truckercore.model.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.model.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.model.z_to_delete.Mapper
import com.example.truckercore.model.shared.utils.expressions.toDate
import com.example.truckercore.model.shared.utils.expressions.toLocalDateTime

internal class BusinessCentralMapper : Mapper {

    override fun toEntity(dto: Dto): BusinessCentral =
        if (dto is BusinessCentralDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = BusinessCentralDto::class.simpleName,
            received = dto::class.simpleName
        )

    override fun toDto(entity: Entity): BusinessCentralDto =
        if (entity is BusinessCentral) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = BusinessCentral::class.simpleName,
            received = entity::class.simpleName
        )

    private fun convertToEntity(dto: BusinessCentralDto) = try {
        BusinessCentral(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!),
            authorizedUserIds = dto.authorizedUserIds ?: hashSetOf(),
            keys = dto.keys!!
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
            persistenceStatus = entity.persistenceStatus.name,
            authorizedUserIds = entity.authorizedUserIds,
            keys = entity.keys
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}
*/
