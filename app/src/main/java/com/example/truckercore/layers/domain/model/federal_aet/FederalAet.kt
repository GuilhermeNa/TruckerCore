package com.example.truckercore.layers.domain.model.federal_aet

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.FederalAetID
import com.example.truckercore.layers.domain.base.others.Period

data class FederalAet(
    override val id: FederalAetID,
    override val url: Url,
    override val companyId: CompanyID,
    override val status: Status,
    override val period: Period
) : Document {


}