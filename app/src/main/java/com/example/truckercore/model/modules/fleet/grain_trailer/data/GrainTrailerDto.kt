package com.example.truckercore.model.modules.fleet.grain_trailer.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID

data class GrainTrailerDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    val transportUnitId: String? = null,
    val plate: String? = null
) : Dto {




}