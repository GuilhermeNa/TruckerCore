package com.example.truckercore.layers.domain.departments.fleet.collections

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.VehicleCollection
import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.model.trailer.Trailer

class TrailerCollection(
    private val dataSet: MutableSet<Trailer> = mutableSetOf(),
) : VehicleCollection<Trailer> {

    override val data get() = dataSet.toSet()

    override fun add(item: Trailer) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Trailer>) {
        dataSet.addAll(items)
    }

    override fun contains(plate: Plate): Boolean =
        dataSet.any { it.plate == plate }

    override fun findBy(plate: Plate): Trailer? =
        dataSet.firstOrNull { trailer -> trailer.plate == plate }

    override fun findBy(id: ID): Trailer? =
        dataSet.firstOrNull { trailer -> trailer.id == id }

}