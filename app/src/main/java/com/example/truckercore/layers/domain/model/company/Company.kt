package com.example.truckercore.layers.domain.model.company

import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity
import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.base.ids.CentralID
import com.example.truckercore.layers.domain.base.ids.CompanyID

data class Company(
    override val id: CompanyID,
    val centralId: CentralID,
    override val persistence: PersistenceState,
) : BaseEntity {




}