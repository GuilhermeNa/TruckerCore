package com.example.truckercore.modules.fleet.trailer.mapper

import com.example.truckercore.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.trailer.enums.TrailerBrand
import com.example.truckercore.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.modules.fleet.trailer.errors.TrailerMappingException
import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

/*
internal class TrailerMapper : Mapper<Trailer, TrailerDto>() {

    override fun handleEntityMapping(entity: Trailer) = TrailerDto(
        businessCentralId = entity.businessCentralId,
        id = entity.id,
        lastModifierId = entity.lastModifierId,
        creationDate = entity.creationDate.toDate(),
        lastUpdate = entity.lastUpdate.toDate(),
        persistenceStatus = entity.persistenceStatus.name,
        plate = entity.plate,
        color = entity.color,
        brand = entity.brand.name,
        category = entity.category.name,
        truckId = entity.truckId
    )

    override fun handleDtoMapping(dto: TrailerDto) = Trailer(
        businessCentralId = dto.businessCentralId!!,
        id = dto.id!!,
        lastModifierId = dto.lastModifierId!!,
        creationDate = dto.creationDate!!.toLocalDateTime(),
        lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
        persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
        plate = dto.plate!!,
        color = dto.color!!,
        brand = TrailerBrand.convertString(dto.brand),
        category = TrailerCategory.convertString(dto.category),
        truckId = dto.truckId
    )

    override fun handleMappingError(receivedException: Exception, obj: Any): Nothing {
        val message = "Error while mapping a ${obj::class.simpleName} object."
        logError(message)
        throw TrailerMappingException(message = "$message Obj: $obj", receivedException)
    }

}*/
