package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.domain.base.enums.PersistenceState

data class CompanyDto(
    override val id: String? = null,
    override val persistenceState: PersistenceState? = null,
    val keysRegistry: List<String>? = null
): BaseDto {

}


