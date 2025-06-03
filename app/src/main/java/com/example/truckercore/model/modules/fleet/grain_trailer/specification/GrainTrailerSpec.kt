package com.example.truckercore.model.modules.fleet.grain_trailer.specification

import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailerDto
import com.example.truckercore.model.modules.fleet.grain_trailer.data.GrainTrailerID

class GrainTrailerSpec(
    override val entityId: GrainTrailerID? = null,
    val transportUnitId: TransportUnitID? = null
): Specification<GrainTrailerDto> {

    override val dtoClass = GrainTrailerDto::class.java

    override fun getFilter(): SearchFilter {
        TODO("Not yet implemented")
    }

}