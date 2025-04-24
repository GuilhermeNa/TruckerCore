package com.example.truckercore.model.modules.notification.data

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.example.truckercore.model.shared.interfaces.data.dto.Dto
import java.util.Date

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
    override fun copyWith(id: String?): BaseDto {
        TODO("Not yet implemented")
    }

}
