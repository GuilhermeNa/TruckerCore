package com.example.truckercore.model.modules.aggregation.transport_unit.data

import com.example.truckercore.model.modules.fleet.truck.data.Truck

data class TransportUnit(
    val truck: Truck,
    val trailerSet: TrailerSet
) {



}