package com.example.truckercore.layers.domain.model.hitch

import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection

class HitchCollection(
    private val dataSet: MutableSet<Hitch> = mutableSetOf()
): DomainCollection<Hitch> {

    override fun add(item: Hitch) {
        TODO("Not yet implemented")
    }

    override fun addAll(items: List<Hitch>) {
        TODO("Not yet implemented")
    }

    override fun toList(): List<Hitch> {
        TODO("Not yet implemented")
    }

    fun getActive(): Hitch? {
        TODO("Not yet implemented")
    }

}