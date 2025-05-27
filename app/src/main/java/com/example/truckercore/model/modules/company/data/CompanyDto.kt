package com.example.truckercore.model.modules.company.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class CompanyDto(
    override val id: String? = null,
    override val persistenceState: PersistenceState? = null,
    val keysRegistry: Set<String>? = null
): BaseDto {

}


