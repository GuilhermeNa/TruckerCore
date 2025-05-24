package com.example.truckercore.model.modules.file.data

import com.example.truckercore.model.modules._shared.enums.PersistenceState
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto

data class FileDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: PersistenceState? = null
): Dto {

}