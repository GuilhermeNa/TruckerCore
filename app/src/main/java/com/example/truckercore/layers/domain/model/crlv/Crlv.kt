package com.example.truckercore.layers.domain.model.crlv

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.entity.VehicleID
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.CrlvID
import com.example.truckercore.layers.domain.base.others.Period

data class Crlv(
    override val id: CrlvID,
    override val url: Url,
    override val companyId: CompanyID,
    override val status: Status,
    override val period: Period,
    val vehicleId: VehicleID
) : Document