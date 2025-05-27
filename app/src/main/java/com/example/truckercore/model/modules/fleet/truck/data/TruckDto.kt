package com.example.truckercore.model.modules.fleet.truck.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet._shared.Plate

class TruckDto(
    override val id: String? = null,
    override val persistenceState: PersistenceState? = null,
    override val companyId: String? = null,
    val transportUnitId: String? = null,
    val plate: String? = null
) : Dto {




}