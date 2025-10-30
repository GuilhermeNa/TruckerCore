package com.example.truckercore.layers.domain.departments.fleet.collections

import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection
import com.example.truckercore.layers.domain.model.trailer.Trailer

class TrailerCollection(
    private val dataSet: MutableSet<Trailer> = mutableSetOf()
) : DomainCollection<Trailer> {

    override fun add(item: Trailer) {
        dataSet.add(item)
    }

    override fun addAll(items: List<Trailer>) {
        dataSet.addAll(items)
    }

    override fun toList(): List<Trailer> = dataSet.toList()

}