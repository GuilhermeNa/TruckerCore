package com.example.truckercore.model.modules.fleet.dump.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class DumpDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    val transportUnitId: String? = null,
    val plate: String? = null
): Dto {



}