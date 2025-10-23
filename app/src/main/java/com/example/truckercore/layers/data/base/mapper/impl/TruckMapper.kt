package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.data.modules._shared._contracts.mapper.Mapper
import com.example.truckercore.data.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.fleet._shared.Plate
import com.example.truckercore.data.modules.fleet.truck.data.Truck
import com.example.truckercore.data.modules.fleet.truck.data.TruckDto
import com.example.truckercore.data.modules.fleet.truck.data.TruckID

object TruckMapper : Mapper<Truck, TruckDto> {

    override fun toDto(entity: Truck): TruckDto = try {
        TruckDto(
            id = entity.id.value,
            companyId = entity.companyId.value,
            persistenceState = entity.persistenceState,
            transportUnitId = entity.transportUnitId?.value,
            plate = entity.plate.value
        )
    } catch (e: Exception) {
        handleError(entity, e)
    }

    override fun toEntity(dto: TruckDto): Truck = try {
        Truck(
            id = TruckID(dto.id!!),
            companyId = CompanyID(dto.companyId!!),
            persistenceState = dto.persistenceState!!,
            transportUnitId = dto.transportUnitId?.let { TransportUnitID(it) },
            plate = Plate(dto.plate!!)
        )
    } catch (e: Exception) {
        handleError(dto, e)
    }

}