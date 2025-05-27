package com.example.truckercore.model.modules.fleet.dry_van.specification

import com.example.truckercore.model.infrastructure.integration.data.for_app.data.collections.SearchFilter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVanDto
import com.example.truckercore.model.modules.fleet.dry_van.data.DryVanID

class DryVanSpec(
    override val entityId: DryVanID? = null,
    val transportUnitId: TransportUnitID? = null
) : Specification<DryVanDto> {

    override val dtoClass = DryVanDto::class.java

    override fun getFilter(): SearchFilter {
        TODO("Not yet implemented")
    }

}