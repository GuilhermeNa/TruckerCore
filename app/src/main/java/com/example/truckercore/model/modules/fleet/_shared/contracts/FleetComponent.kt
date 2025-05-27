package com.example.truckercore.model.modules.fleet._shared.contracts

import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet._shared.Plate

interface FleetComponent {

    val transportUnitId: TransportUnitID?
    val plate: Plate

}