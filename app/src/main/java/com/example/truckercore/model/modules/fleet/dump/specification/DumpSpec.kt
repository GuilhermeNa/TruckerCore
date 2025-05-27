package com.example.truckercore.model.modules.fleet.dump.specification

import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.modules._shared.contracts.entity.ID
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet.dump.data.DumpDto
import com.example.truckercore.model.modules.fleet.dump.data.DumpID

class DumpSpec(
    override val entityId: DumpID? = null,
    val transportUnitId: TransportUnitID
): Specification<DumpDto> {

    override val dtoClass = DumpDto::class.java

    override fun getFilter(): SearchFilter {
        TODO("Not yet implemented")
    }

}