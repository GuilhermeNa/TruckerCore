package com.example.truckercore.modules.user.entity

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.shared.abstractions.Person
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
    val level: Level,
    val person: Person? = null,
    val permissions: Set<Permission> = setOf()
) : Entity {

    fun hasPermission(permission: Permission): Boolean {
        return permissions.contains(permission)
    }

    fun grantPermission(user: User, permission: Permission): User {
        return user.copy(permissions = user.permissions + permission)
    }

    fun revokePermission(user: User, permission: Permission): User {
        return user.copy(permissions = user.permissions - permission)
    }

    fun grantsAccess(person: Person): User {
        TODO()
    }

}
