package com.example.truckercore.modules.fleet.truck.mapper

import com.example.truckercore.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.fleet.truck.enums.TruckBrand
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class TruckMapper : Mapper {

    override fun toEntity(dto: Dto): Truck =
        if (dto is TruckDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(expected = TruckDto::class, received = dto::class)

    override fun toDto(entity: Entity): TruckDto =
        if (entity is Truck) convertToDto(entity)
        else throw IllegalMappingArgumentException(expected = Truck::class, received = entity::class)

    private fun convertToEntity(dto: TruckDto) = try {
        Truck(
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
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: Truck) = try {
        TruckDto(
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
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}
