package com.example.truckercore.model.modules.auditable.data

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import java.util.Date

data class AuditDto(
    override val id: String? = null,
    override val persistence: Persistence? = null,
    val parentId: String? = null,
    val createdBy: String? = null,
    val createdAt: Date? = null,
    val updatedBy: String? = null,
    val updatedAt: Date? = null,
    val deletedBy: String? = null,
    val deletedAt: Date? = null,
): BaseDto