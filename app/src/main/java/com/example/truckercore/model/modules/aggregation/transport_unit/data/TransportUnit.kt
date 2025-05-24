package com.example.truckercore.model.modules.aggregation.transport_unit.data

import com.example.truckercore.model.modules.fleet._shared.contracts.self_propelled.SelfPropelled

data class TransportUnit(
    val tractor: SelfPropelled,
    val attachedUnits: AttachedUnits
) {



}