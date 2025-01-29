package com.example.truckercore.modules.fleet.truck.mapper

import com.example.truckercore.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.fleet.truck.enums.TruckBrand
import com.example.truckercore.modules.fleet.truck.errors.TruckMappingException
import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class TruckMapper : Mapper<Truck, TruckDto>() {

    override fun handleEntityMapping(entity: Truck) = TruckDto(
        businessCentralId = entity.businessCentralId,
        id = entity.id,
        lastModifierId = entity.lastModifierId,
        creationDate = entity.creationDate.toDate(),
        lastUpdate = entity.lastUpdate.toDate(),
        persistenceStatus = entity.persistenceStatus.name,
        plate = entity.plate,
        color = entity.color,
        brand = entity.brand.name
    )

    override fun handleDtoMapping(dto: TruckDto) = Truck(
        businessCentralId = dto.businessCentralId!!,
        id = dto.id!!,
        lastModifierId = dto.lastModifierId!!,
        creationDate = dto.creationDate!!.toLocalDateTime(),
        lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
        persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
        plate = dto.plate!!,
        color = dto.color!!,
        brand = TruckBrand.convertString(dto.brand)
    )

    override fun handleMappingError(receivedException: Exception, obj: Any): Nothing {
        val message = "Error while mapping a ${obj::class.simpleName} object."
        logError(message)
        throw TruckMappingException(message = "$message Obj: $obj", receivedException)
    }

}