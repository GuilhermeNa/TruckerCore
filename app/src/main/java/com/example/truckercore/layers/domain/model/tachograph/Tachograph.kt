package com.example.truckercore.layers.domain.model.tachograph

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.TachographID
import com.example.truckercore.layers.domain.base.ids.TruckID
import com.example.truckercore.layers.domain.base.others.Period

data class Tachograph(
    override val id: TachographID,
    override val companyId: CompanyID,
    override val status: Status,
    override val period: Period,
    override val url: Url,
    val truckID: TruckID,
    val type: TachographType
) : Document