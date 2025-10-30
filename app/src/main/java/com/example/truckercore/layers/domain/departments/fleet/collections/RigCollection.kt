package com.example.truckercore.layers.domain.departments.fleet.collections

import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection
import com.example.truckercore.layers.domain.departments.fleet.objects.Rig

class RigCollection(
    private val dataSet: MutableSet<Rig> = mutableSetOf()
) : DomainCollection<Rig> {

    override fun add(item: Rig) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Rig>) {
        dataSet.addAll(items)
    }

    override fun toList(): List<Rig> = dataSet.toList()

}