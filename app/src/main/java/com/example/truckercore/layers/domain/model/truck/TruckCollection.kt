package com.example.truckercore.layers.domain.model.truck

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.VehicleCollection
import com.example.truckercore.layers.domain.base.others.Plate

class TruckCollection(
    private val dataSet: MutableSet<Truck> = mutableSetOf()
) : VehicleCollection<Truck> {

    override val data get() = dataSet.toSet()

    override fun add(item: Truck) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Truck>) {
        dataSet.addAll(items)
    }

    override fun contains(plate: Plate): Boolean =
        dataSet.any { it.plate == plate }

    override fun findBy(plate: Plate): Truck? =
        dataSet.firstOrNull { truck -> truck.plate == plate }

    override fun findBy(id: ID): Truck? =
        dataSet.firstOrNull { truck -> truck.id == id }

}