package com.example.truckercore.layers.domain.model.driver_assignment

import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection

class DriverAssignmentCollection(
    private val dataSet: Set<DriverAssignment> = emptySet()
): DomainCollection<DriverAssignment> {

    override fun add(item: DriverAssignment) {
        TODO("Not yet implemented")
    }

    override fun addAll(items: List<DriverAssignment>) {
        TODO("Not yet implemented")
    }


    override fun toList(): List<DriverAssignment> {
        TODO("Not yet implemented")
    }

    fun getActive(): DriverAssignment? {
        TODO("Not yet implemented")
    }

}