package com.example.truckercore.model.modules.auditable.data

import com.example.truckercore.model.modules.auditable.data_helper.AuditID
import com.example.truckercore.model.modules.user.data_helper.UserID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.entity.BaseEntity
import java.time.LocalDateTime

data class Audit(
    override val id: AuditID,
    override val persistence: Persistence,
    val createdBy: UserID,
    val createdAt: LocalDateTime,
    val updatedBy: UserID,
    val updatedAt: LocalDateTime,
    val deletedBy: UserID,
    val deletedAt: LocalDateTime,
): BaseEntity