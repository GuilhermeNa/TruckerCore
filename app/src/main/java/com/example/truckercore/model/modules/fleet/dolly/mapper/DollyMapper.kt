package com.example.truckercore.model.modules.fleet.dolly.mapper

import com.example.truckercore.model.modules._shared.contracts.mapper.Mapper
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.modules.fleet.dolly.data.Dolly
import com.example.truckercore.model.modules.fleet.dolly.data.DollyDto
import com.example.truckercore.model.modules.fleet.dolly.data.DollyID

object DollyMapper: Mapper<Dolly, DollyDto> {

    override fun toDto(entity: Dolly): DollyDto = try {
        DollyDto(
            id = entity.id.value,
            companyId = entity.companyId.value,
            persistenceState = entity.persistenceState,
            transportUnitId = entity.transportUnitId?.value,
            plate = entity.plate.value
        )
    } catch (e: Exception) {
        handleError(entity, e)
    }

    override fun toEntity(dto: DollyDto): Dolly = try {
        Dolly(
            id = DollyID(dto.id!!),
            companyId = CompanyID(dto.companyId!!),
            persistenceState = dto.persistenceState!!,
            transportUnitId = dto.transportUnitId?.let { TransportUnitID(it) },
            plate = Plate(dto.plate!!)
        )
    } catch (e: Exception) {
        handleError(dto, e)
    }

}