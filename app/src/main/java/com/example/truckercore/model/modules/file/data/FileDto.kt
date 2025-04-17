package com.example.truckercore.model.modules.file.data

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.dto.Dto

data class FileDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null
): Dto