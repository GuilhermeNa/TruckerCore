package com.example.truckercore._test_data_provider

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entities.PersonalData
import java.time.LocalDateTime
import java.util.Date

internal object TestUserDataProvider {

    fun getBaseEntity() = User(
        centralId = "masterUid",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        level = Level.MASTER,
        permissions = setOf(Permission.VIEW_USER, Permission.CREATE_USER)
    )

    fun getBaseDto() = UserDto(
        centralId = "masterUid",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name,
        level = Level.MASTER.name,
        permissions = listOf(Permission.VIEW_USER.name, Permission.CREATE_USER.name)
    )

}