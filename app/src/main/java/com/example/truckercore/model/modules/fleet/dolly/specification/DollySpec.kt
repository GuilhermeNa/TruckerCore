package com.example.truckercore.model.modules.fleet.dolly.specification

import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet.dolly.data.DollyDto
import com.example.truckercore.model.modules.fleet.dolly.data.DollyID

class DollySpec(
    override val entityId: DollyID? = null,
    val transportUnitId: TransportUnitID
) : Specification<DollyDto> {

    override val dtoClass = DollyDto::class.java

    override fun getFilter(): SearchFilter {
        TODO("Not yet implemented")
    }
}