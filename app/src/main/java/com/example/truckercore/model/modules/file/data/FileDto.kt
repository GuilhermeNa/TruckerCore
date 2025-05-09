package com.example.truckercore.model.modules.file.data

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto

data class FileDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null
): Dto {

}