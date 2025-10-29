package com.example.truckercore.layers.domain.model.hitch

import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection

class HitchCollection(
    private val dataSet: MutableSet<Hitch> = mutableSetOf()
): DomainCollection<Hitch> {

    override fun add(item: Hitch) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Hitch>) {
        dataSet.addAll(items)
    }

    override fun toList(): List<Hitch> {
        return dataSet.toList()
    }

    fun overlapsAny(other: Hitch): Boolean {
        if (dataSet.isEmpty()) return false
        return dataSet.any { it.overlaps(other.period) }
    }

}