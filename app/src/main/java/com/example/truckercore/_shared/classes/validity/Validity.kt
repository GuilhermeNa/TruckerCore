package com.example.truckercore._shared.classes.validity

import java.time.LocalDate

data class Validity(
    val fromDate: LocalDate,
    val toDate: LocalDate
) {

    init {
        if (fromDate >= toDate) throw ValidityException(
            "Validity range is invalid: fromDate ($fromDate) must be before toDate ($toDate)."
        )
    }

    fun isValidOn(date: LocalDate): Boolean = !date.isBefore(fromDate) && !date.isAfter(toDate)

}

class ValidityException(message: String) : Exception(message)