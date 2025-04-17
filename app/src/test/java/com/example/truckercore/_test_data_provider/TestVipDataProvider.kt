package com.example.truckercore._test_data_provider

import com.example.truckercore.model.modules.vip.data.VipDto
import com.example.truckercore.model.modules.vip.data.Vip
import com.example.truckercore.model.shared.utils.expressions.toDate
import java.time.LocalDateTime
import java.util.Date

internal object TestVipDataProvider {

    fun getBaseEntity() = Vip(
        businessCentralId = "centerId",
        id = "vipId",
        lastModifierId = "userId",
        creationDate = LocalDateTime.now(),
        lastUpdate = LocalDateTime.now(),
        persistenceStatus = PersistenceStatus.PERSISTED,
        vipSince = LocalDateTime.now(),
        vipUntil = LocalDateTime.now().plusDays(30),
        notificationDate = LocalDateTime.now().plusDays(20),
        userId = "userId",
        isActive = true
    )

    fun getBaseDto() = VipDto(
        businessCentralId = "centerId",
        id = "vipId",
        lastModifierId = "userId",
        creationDate = Date(),
        lastUpdate = Date(),
        persistenceStatus = PersistenceStatus.PERSISTED.name,
        vipStart = Date(),
        vipEnd = LocalDateTime.now().plusDays(30).toDate(),
        notificationDate = LocalDateTime.now().plusDays(20).toDate(),
        userId = "userId",
        isActive = true
    )

    fun arrInvalidDtosForMapping() = arrayOf(
        getBaseDto().copy(businessCentralId = null),
        getBaseDto().copy(id = null),
        getBaseDto().copy(lastModifierId = null),
        getBaseDto().copy(creationDate = null),
        getBaseDto().copy(lastUpdate = null),
        getBaseDto().copy(persistenceStatus = null),
        getBaseDto().copy(persistenceStatus = "INVALID"),
        getBaseDto().copy(vipStart = null),
        getBaseDto().copy(vipEnd = null),
        getBaseDto().copy(notificationDate = null),
        getBaseDto().copy(userId = null),
        getBaseDto().copy(isActive = null)
    )

    fun arrValidDtosForValidationRules() = arrayOf(
        getBaseDto(),
        getBaseDto().copy(persistenceStatus = PersistenceStatus.ARCHIVED.name)
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
        getBaseDto().copy(persistenceStatus = "INVALID_VALUE"),
        getBaseDto().copy(persistenceStatus = "PENDING"),
        getBaseDto().copy(vipStart = null),
        getBaseDto().copy(vipEnd = null),
        getBaseDto().copy(notificationDate = null),
        getBaseDto().copy(userId = null),
        getBaseDto().copy(userId = ""),
        getBaseDto().copy(userId = " "),
        getBaseDto().copy(isActive = null)
    )

    fun arrValidEntitiesForValidationRules() = arrayOf(
        getBaseEntity(),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED),
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
        getBaseEntity().copy(userId = ""),
        getBaseEntity().copy(userId = " ")
    )

    fun arrValidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(id = null, persistenceStatus = PersistenceStatus.PENDING)
    )

    fun arrInvalidEntitiesForCreationRules() = arrayOf(
        getBaseEntity().copy(businessCentralId = " "),
        getBaseEntity().copy(businessCentralId = ""),
        getBaseEntity().copy(id = ""),
        getBaseEntity().copy(id = " "),
        getBaseEntity().copy(lastModifierId = ""),
        getBaseEntity().copy(lastModifierId = " "),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.PERSISTED),
        getBaseEntity().copy(persistenceStatus = PersistenceStatus.ARCHIVED),
        getBaseEntity().copy(userId = ""),
        getBaseEntity().copy(userId = " ")
    )

}