package com.example.truckercore.layers.domain.departments.fleet.collections

import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection
import com.example.truckercore.layers.domain.model.truck.Truck

class TruckCollection(
    private val dataSet: MutableSet<Truck> = mutableSetOf()
) : DomainCollection<Truck> {

    override fun add(item: Truck) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Truck>) {
        dataSet.addAll(items)
    }

    override fun toList(): List<Truck> = dataSet.toList()

}