package com.example.truckercore.model.modules.aggregation.transport_unit.data

import com.example.truckercore.model.modules.aggregation.transport_unit.data.collection.AttachedUnits
import com.example.truckercore.model.modules.aggregation.transport_unit.exception.TransportUnitException
import com.example.truckercore.model.modules.fleet._shared.contracts.self_propelled.SelfPropelled

data class TransportUnit(
    val transportUnitID: TransportUnitID,
    val tractor: SelfPropelled,
    val attachedUnits: AttachedUnits
) {

    init {
        validate()
    }

    private fun validate() {
        if (tractor.transportUnitId != transportUnitID) throw TransportUnitException(
            "Tractor's transport unit ID does not match the TransportUnit's ID: : $transportUnitID"
        )
        if (attachedUnits.hasInvalidId(transportUnitID)) throw TransportUnitException(
            "One or more attached units have an invalid TransportUnit ID: $transportUnitID."
        )
    }

}