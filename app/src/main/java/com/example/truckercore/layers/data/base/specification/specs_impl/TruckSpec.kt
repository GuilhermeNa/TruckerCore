package com.example.truckercore.layers.data.base.specification.specs_impl

import com.example.truckercore.data.infrastructure.repository.data.contracts.Specification
import com.example.truckercore.data.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.data.modules.fleet.truck.data.TruckDto
import com.example.truckercore.data.modules.fleet.truck.data.TruckID

class TruckSpec(
    override val entityId: TruckID? = null,
    val transportUnitId: TransportUnitID? = null
): Specification<TruckDto> {

    override val dtoClass = TruckDto::class.java

    override fun getFilter(): com.example.truckercore.data.repository.data.abstractions.SearchFilter {
        TODO("Not yet implemented")
    }


}