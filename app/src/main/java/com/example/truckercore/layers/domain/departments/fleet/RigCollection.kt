package com.example.truckercore.layers.domain.departments.fleet

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection
import com.example.truckercore.layers.domain.base.others.Plate

class RigCollection(
    private val dataSet: MutableSet<Rig> = mutableSetOf()
) : DomainCollection<Rig> {

    override val data get() = dataSet.toSet()

    override fun add(item: Rig) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Rig>) {
        dataSet.addAll(items)
    }

    override fun findBy(id: ID): Rig? = null

    fun findBy(plate: Plate): Rig? = dataSet.firstOrNull { rig -> rig.contains(plate) }

}