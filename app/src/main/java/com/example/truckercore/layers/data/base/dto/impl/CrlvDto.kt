package com.example.truckercore.layers.data.base.dto.impl

import com.example.truckercore.core.classes.validity.ValidityDto
import com.example.truckercore.data.infrastructure.repository.data.contracts.Dto
import com.example.truckercore.data.modules._shared.enums.PersistenceState

data class CrlvDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    val validityDto: ValidityDto? = null,
    val refYear: Int? = null,
    val plate: String? = null,
    val url: String? = null
): Dto
