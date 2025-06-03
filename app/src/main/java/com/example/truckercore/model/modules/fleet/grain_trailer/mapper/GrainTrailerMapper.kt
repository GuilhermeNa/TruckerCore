package com.example.truckercore.model.modules.fleet.grain_trailer.mapper

import com.example.truckercore.model.modules._shared._contracts.mapper.Mapper
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.fleet._shared.Plate
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailer
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailerDto
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailerID

object GrainTrailerMapper: Mapper<GrainTrailer, GrainTrailerDto> {

    override fun toDto(entity: GrainTrailer): GrainTrailerDto = try {
        GrainTrailerDto(
            id = entity.id.value,
            companyId = entity.companyId.value,
            persistenceState = entity.persistenceState,
            transportUnitId = entity.transportUnitId?.value,
            plate = entity.plate.value
        )
    } catch (e: Exception) {
        handleError(entity, e)
    }

    override fun toEntity(dto: GrainTrailerDto): GrainTrailer = try {
        GrainTrailer(
            id = GrainTrailerID(dto.id!!),
            companyId = CompanyID(dto.companyId!!),
            persistenceState = dto.persistenceState!!,
            transportUnitId = dto.transportUnitId?.let { TransportUnitID(it) },
            plate = Plate(dto.plate!!)
        )
    } catch (e: Exception) {
        handleError(dto, e)
    }

}