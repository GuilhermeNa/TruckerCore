package com.example.truckercore.layers.domain.model.hitch

import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection

class HitchCollection(
    private val dataSet: MutableSet<Hitch> = mutableSetOf(),
) : DomainCollection<Hitch> {

    override val data get() = dataSet.toSet()

    override fun add(item: Hitch) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Hitch>) {
        dataSet.addAll(items)
    }

    fun overlapsAny(other: Hitch): Boolean {
        if (dataSet.isEmpty()) return false
        return dataSet.any { it.overlaps(other.period) }
    }

    override fun findBy(id: ID): Hitch? =
        dataSet.firstOrNull { hitch -> hitch.id == id }

    fun getCurrent(): List<Hitch> = dataSet.filter { it.isCurrent }

}