package com.example.truckercore.layers.domain.model.hitch

import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.HitchRecordID
import com.example.truckercore.layers.domain.base.ids.TrailerID
import com.example.truckercore.layers.domain.base.ids.TruckID
import com.example.truckercore.layers.domain.base.others.Period

data class Hitch(
    override val id: HitchRecordID,
    override val status: Status,
    override val companyId: CompanyID,
    val truckId: TruckID,
    val trailerIds: Set<TrailerID>,
    val period: Period
) : Entity {




}