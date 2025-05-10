package com.example.truckercore.model.modules.company.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.shared.enums.Persistence

data class CompanyDto(
    override val id: String? = null,
    override val persistence: Persistence? = null,
    val keysRegistry: Set<String>? = null
) : BaseDto {

}


