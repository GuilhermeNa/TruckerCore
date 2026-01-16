package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.layers.data.base.dto.impl.TruckDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.model.truck.Truck

object TruckMapper : Mapper<TruckDto, Truck> {

    override fun toDto(entity: Truck): TruckDto = try {
        TODO()
        /*     TruckDto(
                 id = entity.id.value,
                 companyId = entity.companyId.value,
                 persistenceState = entity.persistenceState,
                 transportUnitId = entity.transportUnitId?.value,
                 plate = entity.plate.value
             )*/
    } catch (e: Exception) {
        TODO()
        //handleError(entity, e)
    }

    override fun toEntity(dto: TruckDto): Truck = try {
        TODO()
        /*    Truck(
                id = TruckID(dto.id!!),
                companyId = CompanyID(dto.companyId!!),
                persistenceState = dto.persistenceState!!,
                transportUnitId = dto.transportUnitId?.let { TransportUnitID(it) },
                plate = Plate(dto.plate!!)
            )*/
    } catch (e: Exception) {
        TODO()
        // handleError(dto, e)
    }

    override fun toDtos(entities: List<Truck>): List<TruckDto> {
        TODO("Not yet implemented")
    }

    override fun toEntities(dtos: List<TruckDto>): List<Truck> {
        TODO("Not yet implemented")
    }

}