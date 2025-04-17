package com.example.truckercore._test_data_provider

import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.enums.PersonCategory
import java.time.LocalDateTime
import java.util.Date

internal object TestUserDataProvider {

    fun getBaseEntity() = User(
        businessCentralId = "",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        isVip = true,
        vipStart = LocalDateTime.now(),
        vipEnd = LocalDateTime.now(),
        level = Level.MASTER,
        permissions = hashSetOf(Permission.VIEW_USER, Permission.CREATE_USER),
        personFLag = PersonCategory.ADMIN
    )

    fun getBaseDto() = UserDto(
        businessCentralId = "",
        id = "id",
        lastModifierId = "lastModifierId",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name,
        isVip = true,
        vipStart = Date(),
        vipEnd = Date(),
        level = Level.MASTER.name,
        permissions = hashSetOf(Permission.VIEW_USER.name, Permission.CREATE_USER.name),
        personFLag = PersonCategory.ADMIN.name
    )

    fun getUserWithPermission(vararg permission: Permission): User {
        return getBaseEntity().copy(permissions = hashSetOf(*permission))
    }

    fun arrInvalidDtos() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(isVip = null),
        getBaseDto().copy(level = null),
        getBaseDto().copy(permissions = null),
        getBaseDto().copy(personFLag = null)
    )

    fun arrValidDtosForValidationRules() = arrayOf(
        getBaseDto(),
        getBaseDto().copy(persistenceStatus = PersistenceStatus.ARCHIVED.name)
    )

    fun arrInvalidDtosForValidationRules() = arrayOf(
        getBaseDto().copy(id = null),
        getBaseDto().copy(id = ""),
        getBaseDto().copy(id = " "),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(lastModifierId = ""),
        getBaseDto().copy(lastModifierId = " "),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(persistenceStatus = ""),
        getBaseDto().copy(persistenceStatus = " "),
        getBaseDto().copy(persistenceStatus = "INVALID_VALUE"),
        getBaseDto().copy(persistenceStatus = "PENDING"),
        getBaseDto().copy(isVip = null),
        getBaseDto().copy(level = null),
        getBaseDto().copy(level = ""),
        getBaseDto().copy(level = " "),
        getBaseDto().copy(level = "INVALID"),
        getBaseDto().copy(permissions = null),
        getBaseDto().copy(permissions = hashSetOf()),
        getBaseDto().copy(permissions = hashSetOf("INVALID_VALUE")),
        getBaseDto().copy(personFLag = null),
        getBaseDto().copy(personFLag = "INVALID")
    )

    fun arrValidEntitiesForValidationRules() = arrayOf(
        getBaseEntity(),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED),
    )

    fun arrInvalidEntitiesForValidationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = "INVALID_VALUE"),
        getBaseEntity().copy(id = null),
        getBaseEntity().copy(id = ""),
        getBaseEntity().copy(id = " "),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PENDING),
        getBaseEntity().copy(permissions = hashSetOf())
    )

    fun arrValidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PENDING)
    )

    fun arrInvalidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = " "),
        getBaseEntity().copy(businessCentralId = "INVALID_VALUE"),
        getBaseEntity().copy(id = ""),
        getBaseEntity().copy(id = " "),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PERSISTED),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED),
        getBaseEntity().copy(permissions = hashSetOf()),
    )

}