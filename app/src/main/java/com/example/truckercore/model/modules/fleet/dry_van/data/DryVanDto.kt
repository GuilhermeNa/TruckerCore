package com.example.truckercore.model.modules.fleet.dry_van.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet._shared.Plate

data class DryVanDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    val transportUnitId: String? = null,
    val plate: String? = null
): Dto {



}
