package com.example.truckercore.modules.user.entity

import android.app.Person
import com.example.truckercore.modules.user.enums.UserRole
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Entity
import java.time.LocalDateTime

data class User(
    override val centralId: String,
    override val id: String?,
    override val lastModifierId: String,
    override val creationDate: LocalDateTime,
    override val lastUpdate: LocalDateTime,
    override val persistenceStatus: PersistenceStatus,
    val userRole: UserRole,
    val person: Person? = null
) : Entity {



}
