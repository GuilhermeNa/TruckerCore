package com.example.truckercore.model.modules.notification.entity

import com.example.truckercore.model.configs.app_constants.Parent
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.interfaces.Entity
import java.time.LocalDateTime

data class Notification(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val title: String,
    val message: String,
    val isRead: Boolean = false,
    val parent: Parent,
    val parentId: String
) : Entity {



}
