package com.example.truckercore.model.modules.notification.dto

import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.interfaces.Dto
import java.util.Date

data class NotificationDto(
    override val businessCentralId: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    val title: String? = null,
    val message: String? = null,
    @field:JvmField
    val isRead: Boolean? = null,
    val parent: String? = null,
    val parentId: String? = null
) : Dto {

    override fun initializeId(newId: String) = this.copy(
        id = newId, persistenceStatus = PersistenceStatus.PERSISTED.name
    )

}
