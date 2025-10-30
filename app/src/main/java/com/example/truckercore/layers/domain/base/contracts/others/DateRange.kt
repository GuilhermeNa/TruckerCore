package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.layers.domain.base.others.Period

interface DateRange {

    val period: Period

    fun overlaps(other: Period): Boolean = period.overlaps(other)

    val isCurrent: Boolean
        get() = period.isCurrent

}