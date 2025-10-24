package com.example.truckercore.layers.domain.base.others

import com.example.truckercore.core.error.DomainException
import java.time.LocalDate

data class Period(val fromDate: LocalDate, val toDate: LocalDate) {

    init {
        validate()
    }

    private fun validate() {
        if (fromDate >= toDate) {
            throw DomainException.RuleViolation(ERROR_MESSAGE)
        }
    }

    private companion object {
        private const val ERROR_MESSAGE =
            "Invalid validity period. The start date must be earlier than the end date."
    }

}

