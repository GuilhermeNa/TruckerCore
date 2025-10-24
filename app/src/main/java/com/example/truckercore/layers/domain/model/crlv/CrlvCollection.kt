package com.example.truckercore.layers.domain.model.crlv

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.files.ONE_MONTH
import java.time.LocalDate

class CrlvCollection(private val dataSet: MutableSet<Crlv> = mutableSetOf()) {

    fun add(crlv: Crlv) {
        if (crlv.overlaps(dataSet))
            throw DomainException.RuleViolation(ERROR_MSG)

        dataSet.add(crlv)
    }

    fun anyActive(): Boolean = dataSet.any { it.isActive }

    fun getActiveCrlv(): Crlv? = dataSet.firstOrNull { it.isActive }

    fun hasCrlvExpiringSoon(withinDays: Long = ONE_MONTH): Boolean {
        val today = LocalDate.now()
        val limit = today.plusDays(withinDays)
        return dataSet.any { it.period.toDate in today..limit }
    }

    fun toList(): List<Crlv> = dataSet.toList()

    private companion object {
        private const val ERROR_MSG = "A CRLV with overlapping validity period already exists."

    }

}