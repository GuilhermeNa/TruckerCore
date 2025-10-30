package com.example.truckercore.layers.domain.base.abstractions

import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection
import java.time.LocalDate

abstract class DocumentCollection<T : Document>(
    private val dataSet: MutableSet<T> = mutableSetOf()
) : DomainCollection<T> {

    override fun add(item: T) {
        dataSet.add(item)
    }

    override fun addAll(items: List<T>) {
        dataSet.addAll(items)
    }

    override fun toList(): List<T> = dataSet.toList()

    fun anyActive(): Boolean = dataSet.any { it.isCurrent }

    fun getActive(): T? = dataSet.firstOrNull { it.isCurrent }

    fun hasExpiringSoon(withinDays: Long): Boolean {
        val today = LocalDate.now()
        val limit = today.plusDays(withinDays)
        return dataSet.any { it.period.toDate in today..limit }
    }

    fun overlapsAny(other: T): Boolean {
        if (dataSet.isEmpty()) return false
        return dataSet.any { it.overlaps(other.period) }
    }

}