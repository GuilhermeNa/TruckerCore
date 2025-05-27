package com.example.truckercore.model.modules.aggregation.transport_unit.data.collection

import com.example.truckercore.model.modules.aggregation.transport_unit.data.TransportUnitID
import com.example.truckercore.model.modules.fleet._shared.contracts.towable.Towable

class AttachedUnits(private val _dataSet: MutableSet<Towable> = mutableSetOf()) {

    val dataSet get() = _dataSet.toSet()

    fun add(towable: Towable){
        _dataSet.add(towable)
    }

    fun hasInvalidId(transportUnitID: TransportUnitID): Boolean {
        return _dataSet.any { it.transportUnitId != transportUnitID }
    }

}