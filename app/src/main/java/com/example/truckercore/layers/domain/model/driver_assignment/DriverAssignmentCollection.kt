package com.example.truckercore.layers.domain.model.driver_assignment

import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection

class DriverAssignmentCollection(
    private val dataSet: MutableSet<DriverAssignment> = mutableSetOf()
) : DomainCollection<DriverAssignment> {

    override fun add(item: DriverAssignment) {
        dataSet.add(item)
    }

    override fun addAll(items: List<DriverAssignment>) {
        dataSet.addAll(items)
    }

    override fun toList(): List<DriverAssignment> {
        return dataSet.toList()
    }

    fun overlapsAny(other: DriverAssignment): Boolean {
        if (dataSet.isEmpty()) return false
        return dataSet.any { it.overlaps(other.period) }
    }

}