package com.example.truckercore.layers.domain.base.abstractions

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.files.ONE_MONTH
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.contracts.others.DomainCollection
import java.time.LocalDate

abstract class DocumentCollection<T : Document>(
    private val dataSet: MutableSet<T> = mutableSetOf()
) : DomainCollection<T> {

    override fun add(item: T) {
        if (item.overlaps(dataSet))
            throw DomainException.RuleViolation(ERROR_MSG)

        dataSet.add(item)
    }

    override fun addAll(item: List<T>) {
        item.forEach { add(it) }
    }

    override fun toList(): List<T> = dataSet.toList()

    fun anyActive(): Boolean = dataSet.any { it.isActive }

    fun getActive(): T? = dataSet.firstOrNull { it.isActive }

    fun hasExpiringSoon(withinDays: Long = ONE_MONTH): Boolean {
        val today = LocalDate.now()
        val limit = today.plusDays(withinDays)
        return dataSet.any { it.period.toDate in today..limit }
    }

    private companion object {
        private const val ERROR_MSG = "A document with overlapping validity period already exists."
    }

}