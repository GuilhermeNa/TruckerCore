package com.example.truckercore.modules.user.entity

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

data class User(
    override val businessCentralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val isVip: Boolean,
    val vipStart: LocalDateTime? = null,
    val vipEnd: LocalDateTime? = null,
    val level: Level,
    val permissions: HashSet<Permission> = hashSetOf(),
    val personFLag: PersonCategory
) : Entity {

    fun hasPermission(permission: Permission): Boolean {
        return permissions.contains(permission)
    }

    fun isVipActive(): Boolean {
        val now = LocalDateTime.now()
        return vipEnd?.let { ve ->
            isVip && ve.isAfter(now)
        } ?: false
    }

}
