package com.example.truckercore.model.modules.notification.data

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto

data class NotificationDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null,
    val title: String? = null,
    val message: String? = null,
    @field:JvmField
    val isRead: Boolean? = null,
    val parent: String? = null,
    val parentId: String? = null,

) : Dto {


}
