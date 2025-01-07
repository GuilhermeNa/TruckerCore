package com.example.truckercore.modules.user.dto

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import java.util.Date

internal data class UserDto(
    override val masterUid: String? = null,
    override val id: String? = null,
    override val lastModifierId: String? = null,
    override val creationDate: Date? = null,
    override val lastUpdate: Date? = null,
    override val persistenceStatus: String? = null,
    val permissionLevel: String? = null
): Dto<User> {

    override fun initializeId(newId: String): Dto<User> {
        return this.copy(
            id = newId,
            persistenceStatus = PersistenceStatus.PERSISTED.name
        )
    }

}