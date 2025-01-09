package com.example.truckercore.modules.business_central.mapper

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.errors.BusinessCentralMappingException
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal object BusinessCentralMapper : Mapper<BusinessCentral, BusinessCentralDto> {

    override fun toDto(entity: BusinessCentral): BusinessCentralDto = try {
        mapEntityToDto(entity)
    } catch (e: Exception) {
        handleMappingToDtoError(e, entity)
    }

    override fun toEntity(dto: BusinessCentralDto): BusinessCentral = try {
        mapDtoToEntity(dto)
    } catch (e: Exception) {
        handleMappingToEntityError(e, dto)
    }

    //----------------------------------------------------------------------------------------------

    private fun mapEntityToDto(entity: BusinessCentral) =
        BusinessCentralDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name
        )

    private fun mapDtoToEntity(dto: BusinessCentralDto): BusinessCentral {
        return BusinessCentral(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!)
        )
    }

    private fun handleMappingToDtoError(e: Exception, entity: BusinessCentral): Nothing {
        logError("Error while mapping a Business Central entity to dto: ${e.message}")
        throw BusinessCentralMappingException(
            message = "Error while mapping a Business Central entity: $entity",
            cause = e
        )
    }

    private fun handleMappingToEntityError(e: Exception, dto: BusinessCentralDto): Nothing {
        logError("Error while mapping a Business Central dto to entity: ${e.message}")
        throw BusinessCentralMappingException(
            message = "Error while mapping a Business Central dto: $dto",
            cause = e
        )
    }

}