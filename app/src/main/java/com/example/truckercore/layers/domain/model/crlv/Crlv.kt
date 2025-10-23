package com.example.truckercore.layers.domain.model.crlv

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.others.Period
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.CrlvID
import java.time.LocalDate

data class Crlv(
    override val id: CrlvID,
    override val url: Url,
    override val companyId: CompanyID,
    override val persistence: PersistenceState,
    val period: Period
) : Document {

    val isActive: Boolean
        get() {
            val today = LocalDate.now()
            return today in period.fromDate..period.toDate
        }

    fun overlaps(other: Period): Boolean = period.overlaps(other)



}
