package com.example.truckercore.model.modules.fleet.truck

import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet.truck.data.TruckDto
import com.example.truckercore.model.modules.fleet.truck.data.TruckID

class TruckSpec(
    override val entityId: TruckID? = null,
    val transportUnitId: TransportUnitID? = null
): Specification<TruckDto> {

    override val dtoClass = TruckDto::class.java

    override fun getFilter(): SearchFilter {
        TODO("Not yet implemented")
    }


}