package com.example.truckercore.layers.domain.model.tachograph

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.contracts.others.ExpirableDocument
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.TachographID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.others.Period

data class Tachograph(
    override val id: TachographID,
    override val url: Url,
    override val companyId: CompanyID,
    override val persistence: PersistenceState,
    override val period: Period,
    val type: TachographType
): ExpirableDocument {



}