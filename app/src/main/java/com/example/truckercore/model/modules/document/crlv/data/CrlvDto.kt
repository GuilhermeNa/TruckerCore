package com.example.truckercore.model.modules.document.crlv.data

import com.example.truckercore._utils.classes.validity.ValidityDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class CrlvDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    val validityDto: ValidityDto? = null,
    val refYear: Int? = null,
    val plate: String? = null,
    val url: String? = null
): Dto
