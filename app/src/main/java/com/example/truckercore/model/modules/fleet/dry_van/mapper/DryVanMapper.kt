package com.example.truckercore.model.modules.fleet.dry_van.mapper

import com.example.truckercore.model.modules._shared.contracts.mapper.Mapper
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVan
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVanDto
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVanID

object DryVanMapper: Mapper<DryVan, DryVanDto> {

    override fun toDto(entity: DryVan): DryVanDto = try {
        DryVanDto(
            id = entity.id.value,
            companyId = entity.companyId.value,
            persistenceState = entity.persistenceState,
            transportUnitId = entity.transportUnitId?.value,
            plate = entity.plate.value
        )
    } catch (e: Exception) {
        handleError(entity, e)
    }

    override fun toEntity(dto: DryVanDto): DryVan = try {
        DryVan(
            id = DryVanID(dto.id!!),
            companyId = CompanyID(dto.companyId!!),
            persistenceState = dto.persistenceState!!,
            transportUnitId = dto.transportUnitId?.let { TransportUnitID(it) },
            plate = Plate(dto.plate!!)
        )
    } catch (e: Exception) {
        handleError(dto, e)
    }

}