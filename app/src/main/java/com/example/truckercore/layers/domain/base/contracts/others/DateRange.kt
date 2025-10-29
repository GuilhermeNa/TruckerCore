package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.layers.domain.base.others.Period
import java.time.LocalDate

interface DateRange {

    val period: Period

    fun overlaps(other: Period): Boolean = period.overlaps(other)

    val isActive: Boolean
        get() {
            if (period.toDate == null) {
                return true
            } else {
                val today = LocalDate.now()
                return today in period.fromDate..period.toDate
            }
        }

}