package com.example.truckercore.layers.domain.model.tachograph

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.files.ONE_MONTH
import java.time.LocalDate

class TachographCollection(private val dataSet: MutableSet<Tachograph> = mutableSetOf()) {

    fun add(tachograph: Tachograph) {
        if (tachograph.overlaps(dataSet))
            throw DomainException.RuleViolation(ERROR_MSG)

        dataSet.add(tachograph)
    }

    fun anyActive(): Boolean = dataSet.any { it.isActive }

    fun getActiveTachograph(): Tachograph? = dataSet.firstOrNull { it.isActive }

    fun hasTachographExpiringSoon(withinDays: Long = ONE_MONTH): Boolean {
        val today = LocalDate.now()
        val limit = today.plusDays(withinDays)
        return dataSet.any { it.period.toDate in today..limit }
    }

    fun toList(): List<Tachograph> = dataSet.toList()

    private companion object {
        private const val ERROR_MSG =
            "A Tachograph with overlapping validity period already exists."
    }

}