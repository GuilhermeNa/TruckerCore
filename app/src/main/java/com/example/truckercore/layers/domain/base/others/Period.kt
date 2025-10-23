package com.example.truckercore.layers.domain.base.others

import com.example.truckercore.core.error.DomainException
import java.time.LocalDate

data class Period(val fromDate: LocalDate, val toDate: LocalDate) {

    init {
        validate()
    }

    private fun validate() {
        if (fromDate >= toDate) {
            throw DomainException.RuleViolation(ERROR_MSG)
        }
    }

    fun overlaps(other: Period): Boolean {
        return this.fromDate <= other.toDate && this.toDate >= other.fromDate
    }

    private companion object {
        private const val ERROR_MSG =
            "Invalid validity period. The start date must be earlier than the end date."
    }

}

