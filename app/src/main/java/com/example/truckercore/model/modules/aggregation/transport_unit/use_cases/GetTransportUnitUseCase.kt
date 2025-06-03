package com.example.truckercore.model.modules.aggregation.transport_unit.use_cases

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnit
import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID

interface GetTransportUnitUseCase {

    suspend operator fun invoke(transportUnitId: TransportUnitID): AppResponse<TransportUnit>

}