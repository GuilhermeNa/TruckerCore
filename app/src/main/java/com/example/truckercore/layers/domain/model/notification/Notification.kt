package com.example.truckercore.layers.domain.model.notification

import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import java.time.LocalDate

data class Notification(
    override val id: ID,
    override val companyId: CompanyID,
    override val status: Status,
    val entityID: ID,
    val read: Boolean,
    val title: String,
    val message: String,
    val creation: LocalDate,
    val type: NotificationType
) : Entity