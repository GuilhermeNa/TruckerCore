package com.example.truckercore._test_data_provider

import com.example.truckercore.model.configs.constants.Parent
import com.example.truckercore.model.modules.notification.data.NotificationDto
import com.example.truckercore.model.modules.notification.data.Notification
import java.time.LocalDateTime
import java.util.Date

internal object TestNotificationDataProvider {

    fun getBaseEntity() = Notification(
        businessCentralId = "123",
        id = "1",
        lastModifierId = "admin",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        title = "Notification",
        message = "Notification text.",
        isRead = false,
        parent = Parent.TRUCK,
        parentId = "truckId"
    )

    fun getBaseDto() = NotificationDto(
        businessCentralId = "123",
        id = "1",
        lastModifierId = "admin",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name,
        title = "Notification",
        message = "Notification text.",
        isRead = false,
        parent = Parent.TRUCK.name,
        parentId = "truckId"
    )

    fun arrInvalidDtosForMapping() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(persistenceStatus = "INVALID"),
        getBaseDto().copy(title = null),
        getBaseDto().copy(message = null),
        getBaseDto().copy(isRead = null),
        getBaseDto().copy(parent = null),
        getBaseDto().copy(parent = "INVALID"),
        getBaseDto().copy(parentId = null)
    )

    fun arrValidDtosForValidationRules() = arrayOf(
        getBaseDto(),
        getBaseDto().copy(persistenceStatus = "ARCHIVED")
    )

    fun arrInvalidDtosForValidationRules() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(businessCentralId = ""),
        getBaseDto().copy(businessCentralId = " "),
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
        getBaseDto().copy(persistenceStatus = "PENDING"),
        getBaseDto().copy(persistenceStatus = "INVALID"),
        getBaseDto().copy(title = null),
        getBaseDto().copy(title = ""),
        getBaseDto().copy(title = " "),
        getBaseDto().copy(message = null),
        getBaseDto().copy(message = ""),
        getBaseDto().copy(message = " "),
        getBaseDto().copy(isRead = null),
        getBaseDto().copy(parent = null),
        getBaseDto().copy(parent = ""),
        getBaseDto().copy(parent = " "),
        getBaseDto().copy(parent = "INVALID"),
        getBaseDto().copy(parentId = null),
        getBaseDto().copy(parentId = ""),
        getBaseDto().copy(parentId = " ")
    )

    fun arrValidEntitiesForValidationRules() = arrayOf(
        getBaseEntity(),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED)
    )

    fun arrInvalidEntitiesForValidationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = ""),
        getBaseEntity().copy(businessCentralId = " "),
        getBaseEntity().copy(id = null),
        getBaseEntity().copy(id = ""),
        getBaseEntity().copy(id = " "),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PENDING),
        getBaseEntity().copy(title = ""),
        getBaseEntity().copy(title = " "),
        getBaseEntity().copy(message = ""),
        getBaseEntity().copy(message = " "),
        getBaseEntity().copy(parentId = ""),
        getBaseEntity().copy(parentId = " ")
    )

    fun arrValidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PENDING)
    )

    fun arrInvalidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = null, businessCentralId = ""),
        getBaseEntity().copy(id = null, businessCentralId = " "),
        getBaseEntity().copy(id = "123"),
        getBaseEntity().copy(id = null, lastModifierId = ""),
        getBaseEntity().copy(id = null, lastModifierId = " "),
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PERSISTED),
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.ARCHIVED),
        getBaseEntity().copy(title = ""),
        getBaseEntity().copy(title = " "),
        getBaseEntity().copy(message = ""),
        getBaseEntity().copy(message = " "),
        getBaseEntity().copy(isRead = true),
        getBaseEntity().copy(parentId = ""),
        getBaseEntity().copy(parentId = " ")
    )

}