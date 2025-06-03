package com.example.truckercore.model.modules.fleet.dump.mapper

import com.example.truckercore.model.modules._shared._contracts.mapper.Mapper
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.modules.fleet.dump.data.Dump
import com.example.truckercore.model.modules.fleet.dump.data.DumpDto
import com.example.truckercore.model.modules.fleet.dump.data.DumpID

object DumpMapper: Mapper<Dump, DumpDto> {

    override fun toDto(entity: Dump): DumpDto = try {
        DumpDto(
            id = entity.id.value,
            companyId = entity.companyId.value,
            persistenceState = entity.persistenceState,
            transportUnitId = entity.transportUnitId?.value,
            plate = entity.plate.value
        )
    } catch (e: Exception) {
        handleError(entity, e)
    }

    override fun toEntity(dto: DumpDto): Dump = try {
        Dump(
            id = DumpID(dto.id!!),
            companyId = CompanyID(dto.companyId!!),
            persistenceState = dto.persistenceState!!,
            transportUnitId = dto.transportUnitId?.let { TransportUnitID(it) },
            plate = Plate(dto.plate!!)
        )
    } catch (e: Exception) {
        handleError(dto, e)
    }

}