package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.layers.domain.base.others.Period
import java.time.LocalDate

interface ExpirableDocument : Document {

    val period: Period

    val isActive: Boolean
        get() {
            val today = LocalDate.now()
            return today in period.fromDate..period.toDate
        }

    fun overlaps(others: Set<ExpirableDocument>): Boolean =
        others.any {
            (period.fromDate <= it.period.toDate) &&
                    (period.toDate >= it.period.fromDate)
        }

}