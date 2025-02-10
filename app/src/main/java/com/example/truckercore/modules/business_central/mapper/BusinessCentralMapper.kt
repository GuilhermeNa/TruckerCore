package com.example.truckercore.modules.business_central.mapper

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.errors.BusinessCentralMappingException
import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

/*
internal class BusinessCentralMapper : Mapper<BusinessCentral, BusinessCentralDto>() {

    override fun toDto(entity: BusinessCentral): BusinessCentralDto = try {
        handleEntityMapping(entity)
    } catch (e: Exception) {
        handleMappingError(e, entity)
    }

    override fun toEntity(dto: BusinessCentralDto): BusinessCentral = try {
        handleDtoMapping(dto)
    } catch (e: Exception) {
        handleMappingError(e, dto)
    }

    //----------------------------------------------------------------------------------------------

    override fun handleEntityMapping(entity: BusinessCentral) =
        BusinessCentralDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name
        )

    override fun handleDtoMapping(dto: BusinessCentralDto): BusinessCentral {
        return BusinessCentral(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!)
        )
    }

    override fun handleMappingError(receivedException: Exception, obj: Any): Nothing {
        val message = "Error while mapping a ${obj::class.simpleName} object."
        logError(message)
        throw BusinessCentralMappingException(message = "$message Obj: $obj", receivedException)
    }

    override fun handleMappingError(obj: Any, cause: Exception): Nothing {
        TODO("Not yet implemented")
    }

}*/
