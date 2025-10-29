package com.example.truckercore.layers.domain.base.others

import com.example.truckercore.core.error.DomainException
import java.time.LocalDate

data class Period(
    val fromDate: LocalDate,
    val toDate: LocalDate? = null
) {

    init {
        if (fromDate >= toDate) {
            throw DomainException.RuleViolation(ERROR_MESSAGE)
        }
    }

    val isCurrent: Boolean
        get() {
            return toDate?.let {
                val today = LocalDate.now()
                return today in fromDate..toDate
            } ?: true
        }

    fun hasExpiration(): Boolean = toDate != null

    fun overlaps(other: Period): Boolean =
        toDate?.let {
            (fromDate <= other.toDate) && (it >= other.fromDate)
        } ?: false


    private companion object {
        private const val ERROR_MESSAGE =
            "Invalid validity period. The start date must be earlier than the end date."
    }

}

