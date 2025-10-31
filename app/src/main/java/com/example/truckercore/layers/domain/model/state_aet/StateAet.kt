package com.example.truckercore.layers.domain.model.state_aet

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.others.Document
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.enums.Uf
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.StateAetID
import com.example.truckercore.layers.domain.base.others.Period

data class StateAet(
    override val id: StateAetID,
    override val period: Period,
    override val url: Url,
    override val companyId: CompanyID,
    override val status: Status,
    val uf: Uf
) : Document {


}