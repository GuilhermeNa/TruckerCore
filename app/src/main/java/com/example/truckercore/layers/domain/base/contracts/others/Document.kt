package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.others.Period
import java.time.LocalDate

interface Document {

    val url: Url

    val period: Period

    val isActive: Boolean
        get() {
            if(period.)
            val today = LocalDate.now()
            return today in period.fromDate..period.toDate
        }

    fun overlaps(others: Set<Document>): Boolean =
        others.any {
            (period.fromDate <= it.period.toDate) &&
                    (period.toDate >= it.period.fromDate)
        }

}