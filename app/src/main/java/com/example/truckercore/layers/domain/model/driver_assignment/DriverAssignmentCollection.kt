package com.example.truckercore.layers.domain.model.driver_assignment

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection

class DriverAssignmentCollection(
    private val dataSet: MutableSet<DriverAssignment> = mutableSetOf()
) : DomainCollection<DriverAssignment> {

    override val data get() = dataSet.toSet()

    override fun findBy(id: ID): DriverAssignment? =
        dataSet.firstOrNull { it.id == id }

    override fun add(item: DriverAssignment) {
        dataSet.add(item)
    }

    override fun addAll(items: List<DriverAssignment>) {
        dataSet.addAll(items)
    }

    fun overlapsAny(other: DriverAssignment): Boolean {
        if (dataSet.isEmpty()) return false
        return dataSet.any { it.overlaps(other.period) }
    }

}