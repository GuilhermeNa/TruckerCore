package com.example.truckercore.modules.business_central.mapper

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.errors.BusinessCentralMappingException
import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class BusinessCentralMapper : Mapper<BusinessCentral, BusinessCentralDto>() {

    override fun toDto(entity: BusinessCentral): BusinessCentralDto = try {
        mapEntityToDto(entity)
    } catch (e: Exception) {
        handleMappingError(e)
    }

    override fun toEntity(dto: BusinessCentralDto): BusinessCentral = try {
        mapDtoToEntity(dto)
    } catch (e: Exception) {
        handleMappingError(e)
    }

    //----------------------------------------------------------------------------------------------

    override fun mapEntityToDto(entity: BusinessCentral) =
        BusinessCentralDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name
        )

    override fun mapDtoToEntity(dto: BusinessCentralDto): BusinessCentral {
        return BusinessCentral(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!)
        )
    }

    override fun handleMappingError(exception: Exception): Nothing {
        logError("Error while mapping a BusinessCentral object.")
        throw BusinessCentralMappingException(cause = exception)
    }

}