package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.data.infrastructure.repository.data.contracts.Dto
import com.example.truckercore.data.modules._shared.enums.PersistenceState

class TruckDto(
    override val id: String? = null,
    override val persistenceState: PersistenceState? = null,
    override val companyId: String? = null,
    val transportUnitId: String? = null,
    val plate: String? = null
) : Dto {




}